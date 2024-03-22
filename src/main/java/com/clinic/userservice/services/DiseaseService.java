package com.clinic.userservice.services;

import com.clinic.userservice.dtos.request.DiseaseRequest;
import com.clinic.userservice.entities.Disease;

import java.util.List;

public interface DiseaseService {

    public Disease getDiseaseById(Long id);
    public List<Disease> getDiseasesBySpeciality(Long specialityId);
    public void createDisease(DiseaseRequest request);
    public void updateDisease(Long id ,DiseaseRequest request);
    public void deleteDisease(Long id);

}
