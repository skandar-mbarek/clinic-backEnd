package com.clinic.userservice.utils;

import com.clinic.userservice.config.TwilioConfiguration;
import com.clinic.userservice.dtos.request.SmsRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class SmsUtil {

    private final TwilioConfiguration twilioConfiguration;


    public void sendSms(SmsRequest smsRequest) {

        PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
        PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
        String message = smsRequest.getMessage();


        MessageCreator creator = Message.creator(to, from, message);
        creator.create();
        log.info("send sms ........... to {} .... from {}..... message :  " + to, from, message);


    }
}
