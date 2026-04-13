package com.example.license_system_gradle.config;

import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class RSAKeyLoader {

    private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsK1FMNjhs7ta/n+BkAVZOXUtj7zV17Lggnlz3psI8Gkn7A3Nv2x7A0CuxZqfbh9qs6W8V2zQbgyHf/5XDJIusW0T40nvL1bQ4TS16V8cfVGJUgxVqPhr/Ja0OYqZA2KkF95FYCkUozURiOhU91yryr5q8WlBoWu4mrRTxwn2OH1dJTUd/qz4fU7ZuavBXOUrBb6JxIA77dqPKLGSMSBWh3LmiwexjFWbraHzZ6qlwfplJuBrj3KjOyuugAtbiKt40V6dSJShzyx37DgX25gi+dON39HA5BT5fOU4Xpxb8A2RYNxnU0Pe5MyhZ1kEBuCKoH00KrJZpqVjInNzhSmOswIDAQAB";

    public PublicKey getPublicKey() throws Exception {

        byte[] keyBytes = Base64.getDecoder().decode(PUBLIC_KEY);

        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);

        KeyFactory factory = KeyFactory.getInstance("RSA");

        return factory.generatePublic(spec);
    }
}