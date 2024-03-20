package com.clinic.userservice.repositories;

import com.clinic.userservice.entities.Patient;
import com.clinic.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByPhoneNumber(String phoneNumber);
}
