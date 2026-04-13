package com.example.license_system_gradle.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Component
public class PrivateKeyLoader {

    private final Dotenv dotenv = Dotenv.configure()
            .directory("./")
            .ignoreIfMissing()
            .load();

    public PrivateKey getPrivateKey() throws Exception {

        String key = dotenv.get("PRIVATE_KEY");

        System.out.println("KEY = " + key); // debug

        if (key == null) {
            throw new RuntimeException("❌ PRIVATE_KEY not found in .env");
        }

        byte[] decoded = Base64.getDecoder().decode(key);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);

        KeyFactory factory = KeyFactory.getInstance("RSA");

        return factory.generatePrivate(spec);
    }
}