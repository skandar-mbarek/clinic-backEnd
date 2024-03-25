package com.clinic.userservice.services.serviceImpl;

import com.clinic.userservice.dtos.*;
import com.clinic.userservice.dtos.request.AuthenticationRequest;
import com.clinic.userservice.dtos.request.DoctorRegisterRequest;
import com.clinic.userservice.dtos.request.PatientRegisterRequest;
import com.clinic.userservice.dtos.request.SmsRequest;
import com.clinic.userservice.entities.*;
import com.clinic.userservice.enumData.Role;
import com.clinic.userservice.exceptions.BadRequestException;
import com.clinic.userservice.repositories.*;
import com.clinic.userservice.services.AthenticationService;
import com.clinic.userservice.services.SpecialityService;
import com.clinic.userservice.utils.OtpUtil;
import com.clinic.userservice.utils.SmsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AthenticationService {

    @Autowired
    private final PatientRepository patientRepository;
    @Autowired
    private final DoctorRepository doctorRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final AddressRepository addressRepository;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final OtpUtil otpUtil;
    @Autowired
    private final SmsUtil smsUtil;
    @Autowired
    private final SpecialityService specialityService;
    @Autowired
    private final ScheduleRepository scheduleRepository;


    public void patientRegister(PatientRegisterRequest request) {


        if (userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()){
            throw new BadRequestException(String.format("this phone number is already exist (%s) ", request.getPhoneNumber()));
        }
        Address address = Address.builder()
                .state(request.getAddress().getState())
                .city(request.getAddress().getCity())
                .country(request.getAddress().getCountry())
                .street(request.getAddress().getStreet())
                .zipCode(request.getAddress().getZipCode())
                .build();
        Long addressId = addressRepository.save(address).getId();
        address = addressRepository.findById(addressId).orElseThrow();
        log.info(address.getCity());
        Patient patient = Patient.builder()
                .firstName(request.getFirstName())
                .lasName(request.getLasName())
                .gender(request.getGendre())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .dateOfBirth(request.getDateOfBirth())
                .role(Role.PATIENT)
                .status(false)
                .build();


            String otp = otpUtil.generateOTP();
            String message = String.valueOf("your validate Number : /n"+otp);
            SmsRequest smsRequest = new SmsRequest(request.getPhoneNumber(), message);
            smsUtil.sendSms(smsRequest);
            log.info("sms Sended ......");
            patient.setOtp(otp);
            patientRepository.save(patient);
            log.info("user saved sucess ....  %s".formatted(request.getPhoneNumber()));


    }


    public void DoctorRegister(DoctorRegisterRequest request) {

        if (userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()){
            throw new BadRequestException(String.format("this phone number is already exist (%s) ", request.getPhoneNumber()));
        }

        String otp = otpUtil.generateOTP();
        String message = String.valueOf("your validate Number : "+otp);
        SmsRequest smsRequest = new SmsRequest(request.getPhoneNumber(), message);
        //        otpUtil.sendSms(smsRequest);

        Address address = Address.builder()
                .state(request.getAddress().getState())
                .city(request.getAddress().getCity())
                .country(request.getAddress().getCountry())
                .street(request.getAddress().getStreet())
                .zipCode(request.getAddress().getZipCode())
                .build();
        Long addressId = addressRepository.save(address).getId();
        address = addressRepository.findById(addressId).orElseThrow();
        log.info(address.getCity());

        Schedule schedule = Schedule.builder()
                .days(request.getSchedule().getDays())
                .timeFrom(request.getSchedule().getTimeFrom())
                .timeTo(request.getSchedule().getTimeTo())
                .consultationDuration(request.getSchedule().getConsultationDuration())
                .build();
        Long scheduleId = scheduleRepository.save(schedule).getId();
        schedule = scheduleRepository.findById(scheduleId).orElseThrow();


        var speciality = specialityService.getById(request.getSpecialityId());
        log.info(speciality.getName(),speciality.getDescription());

        Doctor doctor = Doctor.builder()
                .firstName(request.getFirstName())
                .lasName(request.getLasName())
                .gender(request.getGendre())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .address(address)
                .schedule(schedule)
                .dateOfBirth(request.getDateOfBirth())
                .role(Role.DOCTOR)
                .status(false)
                .licenceNumber(request.getLicenceNumber())
                .licenceDate(request.getLicenceDate())
                .speciality(speciality)
                .build();



        log.info("sms Sended ......");
        doctor.setOtp(otp);
        doctorRepository.save(doctor);
        log.info("user saved sucess ....  %s".formatted(request.getPhoneNumber()));

    }

    public AuthenticationResponse verifyOTP (String phoneNumber , String otp){

        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                ()-> new BadRequestException(String.format("this phone number is not exist (%s)" , phoneNumber))
        );
        if (otp.equals(user.getOtp())){
            user.setStatus(true);
            user.setOtp(null);
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
        throw new BadRequestException("invalid OTP");
    }


    public AuthenticationResponse authentication(AuthenticationRequest request){

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getPhoneNumber(),
                            request.getPassword()
                    )
            );

        var user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
        }
        catch (AuthenticationException e){
        log.error(e.getMessage());
        throw new BadRequestException("Sorry, your password is incorrect. Please check your password.");
    }
    }

    public void forgotPassword (String phoneNumber){
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                ()->new BadRequestException(String.format("this phone number is not exist (%s)" , phoneNumber))
        );

        String otp = otpUtil.generateOTP();
        String message = String.valueOf("your validate Number : "+otp);
        SmsRequest smsRequest = new SmsRequest(phoneNumber, message);
        smsUtil.sendSms(smsRequest);
        log.info("sms Sended ......");
        user.setOtp(otp);
        userRepository.save(user);
    }
    public AuthenticationResponse resetPassword(String phoneNumber , String otp ,String newPassword){

        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                ()->  new BadRequestException(String.format("this phone number is not exist (%s)" , phoneNumber))
        );

        if (otp.equals(user.getOtp())){
            String hashedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(hashedPassword);
            user.setOtp(null);
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
        else{
            throw new BadRequestException("Invalid OTP !");
        }
    }
}
