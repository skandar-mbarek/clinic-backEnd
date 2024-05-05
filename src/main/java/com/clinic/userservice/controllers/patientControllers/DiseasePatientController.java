package com.clinic.userservice.controllers.patientControllers;

import com.clinic.userservice.constants.Constants;
import com.clinic.userservice.entities.Disease;
import com.clinic.userservice.services.DiseaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APP_ROOT_PATIENT +"/disease")
@CrossOrigin("*")
@Tag(name = "Disease Management")
public class DiseasePatientController {

    @Autowired
    private final DiseaseService service;

    @Operation(summary = "Get Diseases By Speciality")
    @GetMapping("get-by-speciality/{specialityId}")
    public ResponseEntity<List<Disease>> getDiseaseBySpeciality(@PathVariable Long specialityId){
        return ResponseEntity.ok(service.getDiseasesBySpeciality(specialityId));
    }
    @Operation(summary = "Get Disease By Id")
    @GetMapping("{id}")
    public ResponseEntity<Disease> getDiseaseById(@PathVariable Long id){
        return ResponseEntity.ok(service.getDiseaseById(id));
    }

}
