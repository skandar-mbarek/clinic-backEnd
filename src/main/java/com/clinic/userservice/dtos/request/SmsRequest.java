package com.clinic.userservice.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SmsRequest {

    private String phoneNumber;
    private String message;


}
