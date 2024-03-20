package com.clinic.userservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("twilio")
public class TwilioConfiguration {

    private String accountSid;
    private String authToken;
    private String trialNumber;
}
