package com.example.license_system.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class KeyGeneratorUtil {

    public static void main(String[] args) throws Exception {

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);

        KeyPair pair = keyGen.generateKeyPair();

        String publicKey = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());

        System.out.println("PUBLIC KEY:\n" + publicKey);
        System.out.println("\nPRIVATE KEY:\n" + privateKey);
    }
}
