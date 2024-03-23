package com.clinic.userservice.controllers.doctorControllers;

import com.clinic.userservice.constants.Constants;
import com.clinic.userservice.entities.Disease;
import com.clinic.userservice.services.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APP_ROOT_DOCTOR +"/disease")
@CrossOrigin("*")
public class DiseaseDoctorController {

    @Autowired
    private final DiseaseService service;

    @GetMapping("get-by-speciality/{specialityId}")
    public ResponseEntity<List<Disease>> getDiseaseBySpeciality(@PathVariable Long specialityId){
        return ResponseEntity.ok(service.getDiseasesBySpeciality(specialityId));
    }
    @GetMapping("{id}")
    public ResponseEntity<Disease> getDiseaseById(@PathVariable Long id){
        return ResponseEntity.ok(service.getDiseaseById(id));
    }
}
