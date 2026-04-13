package com.example.license_system_gradle.service;

import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class LicenseValidator {

    public static boolean verify(String data, String signatureStr, PublicKey publicKey) throws Exception {

        Signature signature = Signature.getInstance("SHA256withRSA");

        signature.initVerify(publicKey);
        signature.update(data.getBytes());

        byte[] signatureBytes = Base64.getDecoder().decode(signatureStr);

        return signature.verify(signatureBytes);
    }
}