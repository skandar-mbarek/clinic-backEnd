package com.clinic.userservice.services.serviceImpl;

import com.clinic.userservice.dtos.PatientDto;
import com.clinic.userservice.entities.Patient;
import com.clinic.userservice.exceptions.BadRequestException;
import com.clinic.userservice.repositories.PatientRepository;
import com.clinic.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private final PatientRepository repository;
    @Override
    public Patient getMe() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPhone = authentication.getName();
        Optional<Patient> me = repository.findByPhoneNumber(currentPhone);
        if (me.isPresent()){
            return me.get();
        }else throw new BadRequestException("this user is not exist");
    }
}
