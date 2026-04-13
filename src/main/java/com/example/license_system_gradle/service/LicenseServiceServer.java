package com.example.license_system_gradle.service;

import com.example.license_system_gradle.dto.LicenseData;
import com.example.license_system_gradle.config.PrivateKeyLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class LicenseServiceServer {

    private final PrivateKeyLoader keyLoader;

    // ✅ Correct constructor injection
    public LicenseServiceServer(PrivateKeyLoader keyLoader) {
        this.keyLoader = keyLoader;
    }
    public Map<String, String> generateLicense(LicenseData data) {
        try {

            ObjectMapper mapper = new ObjectMapper();

            // ✅ Step 1: Convert object → JSON
            String json = mapper.writeValueAsString(data);

            // ✅ Step 2: Encode
            String encodedData = Base64.getEncoder().encodeToString(json.getBytes());

            // ✅ Step 3: Load Private Key
            PrivateKey privateKey = keyLoader.getPrivateKey();

            // ✅ Step 4: Sign
            String signature = LicenseSigner.sign(encodedData, privateKey);

            // ✅ Step 5: Create license file structure
            Map<String, String> license = new HashMap<>();
            license.put("data", encodedData);
            license.put("signature", signature);

            return license;

        } catch (Exception e) {
            throw new RuntimeException("License generation failed", e);
        }
    }
}