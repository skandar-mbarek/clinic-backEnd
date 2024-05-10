package com.clinic.userservice.controllers.doctorControllers;

import com.clinic.userservice.constants.Constants;
import com.clinic.userservice.dtos.request.ChronicDiseaseRequest;
import com.clinic.userservice.services.ChronicDiseaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APP_ROOT_DOCTOR +"/chronic-disease")
@CrossOrigin("*")
@Tag(name = "Chronic Disease Management")
public class ChronicDiseaseDoctorController {

    @Autowired
    private final ChronicDiseaseService service;

    @Operation(summary = "Create Chronic Disease ")
    @PostMapping
    public ResponseEntity<String> createChronicDisease(@RequestBody ChronicDiseaseRequest request){
        service.createChronicDisease(request);
        return ResponseEntity.ok("save success !!");
    }

}
