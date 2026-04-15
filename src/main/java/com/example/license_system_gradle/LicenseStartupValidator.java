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
    @Scheduled(fixedRate = 2 * 60 * 60 * 1000)
    public void validateOnStartup() {

        boolean valid = licenseService.validateLicense();

        if (!valid) {
            throw new RuntimeException("❌ Invalid License - Application Stopped");
        }

        log.info("🚀 License verified at startup");
    }

}