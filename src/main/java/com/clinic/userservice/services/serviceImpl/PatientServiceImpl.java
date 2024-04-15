package com.clinic.userservice.services.serviceImpl;

import com.clinic.userservice.dtos.request.UpdatePatientRequest;
import com.clinic.userservice.entities.Patient;
import com.clinic.userservice.exceptions.BadRequestException;
import com.clinic.userservice.repositories.PatientRepository;
import com.clinic.userservice.services.PatientService;
import com.clinic.userservice.utils.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class PatientServiceImpl implements PatientService {

    @Autowired
    private final PatientRepository repository;
    @Autowired
    private final FileStorage fileStorage;
    @Override
    public void updatePatient(Long id, UpdatePatientRequest request) {

        Patient patient = repository.findById(id).orElseThrow(
                ()-> new BadRequestException("this patient is not exist")
        );
        if (request.getImageName() !=null){
            patient.setImageName(request.getImageName());
        }
        if (request.getFirstName() != null){
            patient.setFirstName(request.getFirstName());
        }
        if (request.getLastName()!= null){
            patient.setLastName(request.getLastName());
        }
        if (request.getGender()!=null){
            patient.setGender(request.getGender());
        }
        if (request.getEmail()!= null){
            patient.setEmail(request.getEmail());
        }
        if (request.getDateOfBirth()!=null){
            patient.setDateOfBirth(request.getDateOfBirth());
        }
        repository.save(patient);
    }
}
