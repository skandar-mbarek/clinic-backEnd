package com.clinic.userservice.config;

import com.twilio.Twilio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class TwilioInitializr {


    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    public TwilioInitializr(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
        Twilio.init(
                twilioConfiguration.getAccountSid(),
                twilioConfiguration.getAuthToken()
        );
        log.info("Twilio initialized .........with account sid {} ", twilioConfiguration.getAccountSid());
    }
}
