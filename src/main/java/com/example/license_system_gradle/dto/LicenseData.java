package com.example.license_system_gradle.dto;

import lombok.Data;

@Data
public class LicenseData {

    private String clientName;
    private String expiryDate;
    private String enabled;
}