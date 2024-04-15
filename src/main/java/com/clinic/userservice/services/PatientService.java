package com.clinic.userservice.services;

import com.clinic.userservice.dtos.request.UpdatePatientRequest;
import org.springframework.web.multipart.MultipartFile;

public interface PatientService {

    void updatePatient (Long id, UpdatePatientRequest request);


}
