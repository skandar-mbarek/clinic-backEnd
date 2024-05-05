package com.clinic.userservice.controllers;

import com.clinic.userservice.dtos.request.AuthenticationRequest;
import com.clinic.userservice.dtos.AuthenticationResponse;
import com.clinic.userservice.dtos.request.DoctorRegisterRequest;
import com.clinic.userservice.dtos.request.PatientRegisterRequest;
import com.clinic.userservice.services.AthenticationService;
import com.clinic.userservice.constants.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication")
public class AuthenticationController {

    @Autowired
    private final AthenticationService authenticationService;

    @Operation(summary = "Doctor Register")
    @PostMapping("/register/doctor")
    public ResponseEntity<String> doctorRegister(@RequestBody  DoctorRegisterRequest request) {

        authenticationService.DoctorRegister(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("save success");
    }
    @Operation(summary = "Patient Register")
    @PostMapping("/register/patient")
    public ResponseEntity<String> patientRegister(@RequestBody @Valid PatientRegisterRequest request) {

        authenticationService.patientRegister(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("save success");
    }

    @Operation(summary = "Verify OTP")
    @PostMapping("/verify")
    public ResponseEntity<AuthenticationResponse> VerifyOtp(@RequestParam String phoneNumber, @RequestParam String otp) {

        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.verifyOTP(phoneNumber, otp));

    }

    @Operation(summary = "Authentication")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authentication(@RequestBody @Valid AuthenticationRequest request) {

        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.authentication(request));

    }
    @Operation(summary = "Forgot Password")
    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword (@RequestParam String phoneNumber){
        authenticationService.forgotPassword(phoneNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap("message","OTP send to your phone")
        );
    }
    @Operation(summary = "Reset Password")
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
