package com.example.license_system_gradle.service;

import com.example.license_system_gradle.config.RSAKeyLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.PublicKey;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Map;

@Slf4j
@Service
public class LicenseService {

    private final RSAKeyLoader keyLoader;
    private final ObjectMapper mapper;
    private final ResourceLoader resourceLoader;

    private final String licensePath;

    public LicenseService(
            RSAKeyLoader keyLoader,
            ObjectMapper mapper,
            ResourceLoader resourceLoader,
            @Value("${license.file.path}") String licensePath
    ) {
        this.keyLoader = keyLoader;
        this.mapper = mapper;
        this.resourceLoader = resourceLoader;
        this.licensePath = licensePath;

        log.info("📄 License Path configured: {}", licensePath);

        if (licensePath == null || licensePath.isEmpty()) {
            throw new IllegalArgumentException("❌ license.file.path is missing in application.properties");
        }
    }

    public boolean validateLicense() {
        try {

            // ✅ Load file (supports file system OR classpath)
            Resource resource = resourceLoader.getResource(licensePath);

            if (!resource.exists()) {
                log.error("❌ License file not found at: {}", licensePath);
                return false;
            }

            // ✅ Read file content safely
            String content = new String(
                    resource.getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8
            );

            // Parse JSON
            Map<String, String> license = mapper.readValue(content, Map.class);

            String data = license.get("data");
            String signature = license.get("signature");

            if (data == null || signature == null) {
                log.error("❌ Invalid license format (missing data/signature)");
                return false;
            }

            PublicKey publicKey = keyLoader.getPublicKey();

            // ✅ Step 1: Verify signature
            boolean isValidSignature =
                    LicenseValidator.verify(data, signature, publicKey);

            if (!isValidSignature) {
                log.error("❌ Invalid License Signature");
                return false;
            }

            // ✅ Step 2: Decode payload
            String decodedJson =
                    new String(Base64.getDecoder().decode(data));

            Map<String, Object> licenseData =
                    mapper.readValue(decodedJson, Map.class);

            // ✅ Step 3: Expiry check
            String expiry = (String) licenseData.get("expiryDate");

            if (expiry == null || LocalDate.now().isAfter(LocalDate.parse(expiry))) {
                log.warn("❌ License Expired");
                return false;
            }

            // ✅ Step 4: Enabled check
            String enabled = String.valueOf(licenseData.get("enabled"));

            if (enabled == null || !enabled.equalsIgnoreCase("true")) {
                log.warn("❌ License Disabled");
                return false;
            }

            // ✅ Success
            log.info("✅ License Valid");
            log.info("👤 Client: {}", licenseData.get("clientName"));

            return true;

        } catch (Exception e) {
            log.error("❌ License validation failed due to error", e);
            return false;
        }
    }
}