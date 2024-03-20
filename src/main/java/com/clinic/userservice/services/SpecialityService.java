package com.clinic.userservice.services;

import com.clinic.userservice.dtos.request.SpecialityRequest;
import com.clinic.userservice.entities.Speciality;

import java.util.List;

public interface SpecialityService {

    public void create(SpecialityRequest specialityRequest);
    public List<Speciality> getAll();
    public Speciality getById(Long id);

}
