package com.example.license_system_gradle.service;

import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

public class LicenseSigner {

    public static String sign(String data, PrivateKey privateKey) throws Exception {

        Signature signature = Signature.getInstance("SHA256withRSA");

        signature.initSign(privateKey);
        signature.update(data.getBytes());

        byte[] signedBytes = signature.sign();

        return Base64.getEncoder().encodeToString(signedBytes);
    }
}