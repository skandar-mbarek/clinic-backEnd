package com.clinic.userservice.utils;

import com.clinic.userservice.config.TwilioConfiguration;
import com.clinic.userservice.dtos.request.SmsRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
@Slf4j
public class OtpUtil {


    public String generateOTP() {
        int otp = ThreadLocalRandom.current().nextInt(100000, 1000000);
        return String.valueOf(otp);
    }


}
