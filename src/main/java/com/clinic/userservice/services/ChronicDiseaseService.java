package com.clinic.userservice.services;

import com.clinic.userservice.dtos.request.ChronicDiseaseRequest;
import com.clinic.userservice.entities.ChronicDisease;

import java.util.List;

public interface ChronicDiseaseService {

    List<ChronicDisease> getChronicDiseasesByPatientId(Long patientId);
    void createChronicDisease(ChronicDiseaseRequest request);

}
