package com.clinic.userservice.controllers.patientControllers;


import com.clinic.userservice.entities.Speciality;
import com.clinic.userservice.services.SpecialityService;
import com.clinic.userservice.constants.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APP_ROOT_PATIENT +"/speciality")
@CrossOrigin("*")
@Tag(name = "Speciality Management")
public class SpecialityPatientController {

    @Autowired
    private final SpecialityService service;

    @Operation(summary = "Get All Specialities")
    @GetMapping
    public ResponseEntity<List<Speciality>> getAllSpeciality (){
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get Speciality By Id")
    @GetMapping("{id}")
    public ResponseEntity<Speciality> getSpecialityById (@PathVariable Long id){
        return ResponseEntity.ok(service.getById(id));
    }


}
