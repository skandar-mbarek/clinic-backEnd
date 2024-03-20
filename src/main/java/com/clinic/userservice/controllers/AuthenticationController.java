package com.clinic.userservice.controllers;

import com.clinic.userservice.dtos.request.AuthenticationRequest;
import com.clinic.userservice.dtos.AuthenticationResponse;
import com.clinic.userservice.dtos.request.DoctorRegisterRequest;
import com.clinic.userservice.dtos.request.PatientRegisterRequest;
import com.clinic.userservice.services.AthenticationService;
import com.clinic.userservice.constants.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APP_ROOT_AUTH)
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private final AthenticationService authenticationService;

    @PostMapping("/register/doctor")
    public ResponseEntity<String> doctorRegister(@RequestBody DoctorRegisterRequest request) {

        authenticationService.DoctorRegister(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("save success");
    }

    @PostMapping("/register/patient")
    public ResponseEntity<String> patientRegister(@RequestBody PatientRegisterRequest request) {

        authenticationService.patientRegister(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("save success");
    }

    @PostMapping("/verify")
    public ResponseEntity<AuthenticationResponse> patientRegister(@RequestParam String phoneNumber, @RequestParam String otp) {

        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.verifyOTP(phoneNumber, otp));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authentication(@RequestBody @Valid AuthenticationRequest request) {

        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.authentication(request));

    }
    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword (@RequestParam String phoneNumber){
        authenticationService.forgotPassword(phoneNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap("message","OTP send to your phone")
        );
    }
    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(
            @RequestParam String phoneNumber,
            @RequestParam String otp,
            @RequestParam String newPassword
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                authenticationService.resetPassword(phoneNumber, otp, newPassword));
    }




}
