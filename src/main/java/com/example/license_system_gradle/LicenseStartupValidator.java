package com.example.license_system_gradle;

import com.example.license_system_gradle.service.LicenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LicenseStartupValidator {

    private final LicenseService licenseService;

    public LicenseStartupValidator(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void validateOnStartup() {

        boolean valid = licenseService.validateLicense();

        if (!valid) {
            throw new RuntimeException("❌ Invalid License - Application Stopped");
        }

        log.info("🚀 License verified at startup");
    }
    @Scheduled(fixedRate = 2 * 60 * 60 * 1000)
    public void checkLicensePeriodically() {

        log.info("🔍 Running scheduled license check...");

        boolean valid = licenseService.validateLicense();

        if (!valid) {
            log.error("❌ License invalid - system should stop or restrict access");

            // You can choose behavior:
            // Option 1: stop app
            throw new RuntimeException("❌ License invalid during runtime");
        }

        log.info("✅ License still valid");
    }
}