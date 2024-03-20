package com.clinic.userservice.services;

import com.clinic.userservice.dtos.AuthenticationResponse;
import com.clinic.userservice.dtos.request.AuthenticationRequest;
import com.clinic.userservice.dtos.request.DoctorRegisterRequest;
import com.clinic.userservice.dtos.request.PatientRegisterRequest;

public interface AthenticationService {
    public void patientRegister(PatientRegisterRequest request);
    public void DoctorRegister(DoctorRegisterRequest request);
    public AuthenticationResponse verifyOTP (String phoneNumber , String otp);
    public AuthenticationResponse authentication(AuthenticationRequest request);
    public void forgotPassword (String phoneNumber);
    public AuthenticationResponse resetPassword(String phoneNumber , String otp ,String newPassword);
}
