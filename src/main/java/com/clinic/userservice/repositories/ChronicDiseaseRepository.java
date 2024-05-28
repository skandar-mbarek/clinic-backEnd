package com.clinic.userservice.repositories;

import com.clinic.userservice.entities.ChronicDisease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChronicDiseaseRepository extends JpaRepository<ChronicDisease,Long> {

    List<ChronicDisease> findAllByPatientUserId(Long patientId);
}
