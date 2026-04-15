package com.example.license_system_gradle.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
@Component
public class PrivateKeyLoader {

    private final String privateKeyStr;

    public PrivateKeyLoader() {

        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();   // ✅ auto reads from project root

        this.privateKeyStr = dotenv.get("PRIVATE_KEY");

        System.out.println("DEBUG PRIVATE_KEY = " + privateKeyStr);

        if (privateKeyStr == null || privateKeyStr.isEmpty()) {
            throw new RuntimeException("❌ PRIVATE_KEY missing in .env file");
        }
    }

    public PrivateKey getPrivateKey() throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyStr);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }
}