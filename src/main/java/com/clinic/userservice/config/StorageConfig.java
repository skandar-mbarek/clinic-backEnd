package com.clinic.userservice.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;

@Configuration
@Slf4j
public class StorageConfig {


    @Value("${upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            if (uploadDir.mkdirs()) {
                log.info("Upload directory created successfully: {}", uploadDir.getAbsolutePath());
            } else {
                log.error("Failed to create upload directory: {}", uploadDir.getAbsolutePath());
            }
        } else {
            log.info("Upload directory already exists: {}", uploadDir.getAbsolutePath());
        }
    }
}
