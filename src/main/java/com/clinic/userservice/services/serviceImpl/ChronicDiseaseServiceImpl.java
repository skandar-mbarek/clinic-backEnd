package com.clinic.userservice.services.serviceImpl;

import com.clinic.userservice.dtos.request.ChronicDiseaseRequest;
import com.clinic.userservice.dtos.request.MedicineRequest;
import com.clinic.userservice.entities.*;
import com.clinic.userservice.repositories.ChronicDiseaseRepository;
import com.clinic.userservice.services.ChronicDiseaseService;
import com.clinic.userservice.services.DiseaseService;
import com.clinic.userservice.services.DoctorService;
import com.clinic.userservice.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChronicDiseaseServiceImpl implements ChronicDiseaseService {

    @Autowired
    private final ChronicDiseaseRepository repository;
    @Autowired
    private final DoctorService doctorService;
    @Autowired
    private final PatientService patientService;
    @Autowired
    private final DiseaseService diseaseService;

    @Override
    public List<ChronicDisease> getChronicDiseasesByPatientId(Long patientId) {
        return repository.findAllByPatientUserId(patientId);
    }

    @Override
    public void createChronicDisease(ChronicDiseaseRequest request) {

        Doctor doctor = doctorService.getDoctorById(request.getDoctorId());
        Patient patient = patientService.getPatientByID(request.getPatientId());
        Disease disease = null;
        if (request.getDiseaseId()!=null){
            disease = diseaseService.getDiseaseById(request.getDiseaseId());
        }
        List<Medicine> medicines = new ArrayList<>();

        if (request.getMedicines() != null) {
            for (MedicineRequest medicineRequest : request.getMedicines()) {
                Medicine medicine = Medicine.builder()
                        .name(medicineRequest.getName())
                        .description(medicineRequest.getDescription())
                        .build();
                medicines.add(medicine);
            }
        }

        ChronicDisease chronicDisease = ChronicDisease.builder()
                .conditionType(request.getConditionType())
                .title(request.getTitle())
                .description(request.getDescription())
                .doctor(doctor)
                .patient(patient)
                .disease(disease)
                .medicines(medicines)
                .build();

        repository.save(chronicDisease);


    }
}
