package com.clinic.userservice.services;

import com.clinic.userservice.dtos.PatientDto;
import com.clinic.userservice.entities.Patient;

public interface UserService {

    public Patient getMe ();

}
