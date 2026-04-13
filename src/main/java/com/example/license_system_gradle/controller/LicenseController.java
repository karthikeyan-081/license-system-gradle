package com.example.license_system_gradle.controller;

import com.example.license_system_gradle.service.LicenseServiceServer;
import com.example.license_system_gradle.dto.LicenseData;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/license")
public class LicenseController {

    private final LicenseServiceServer service;

    public LicenseController(LicenseServiceServer service) {
        this.service = service;
    }

    @PostMapping("/generate")
    public Map<String, String> generate(@RequestBody LicenseData data) {
        return service.generateLicense(data);
    }
}