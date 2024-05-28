package com.clinic.userservice.controllers.patientControllers;

import com.clinic.userservice.constants.Constants;
import com.clinic.userservice.entities.ChronicDisease;
import com.clinic.userservice.services.ChronicDiseaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APP_ROOT_PATIENT+"/chronic-disease")
@CrossOrigin("*")
@Tag(name = "Chronic Disease Management")
public class ChronicDiseasePatientController {

    @Autowired
    private final ChronicDiseaseService service;

    @GetMapping("{patientId}")
    public ResponseEntity<List<ChronicDisease>> getChronicDiseaseByPatient(@PathVariable Long patientId){
        return ResponseEntity.ok(service.getChronicDiseasesByPatientId(patientId));
    }

}
