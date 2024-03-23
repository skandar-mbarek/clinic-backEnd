package com.clinic.userservice.controllers.adminControllers;

import com.clinic.userservice.constants.Constants;
import com.clinic.userservice.dtos.request.DiseaseRequest;
import com.clinic.userservice.entities.Disease;
import com.clinic.userservice.services.DiseaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APP_ROOT_ADMIN +"/disease")
@CrossOrigin("*")
public class DiseaseAdminController {

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

    @PostMapping
    public ResponseEntity<String> createDisease(@RequestBody @Valid DiseaseRequest request){
        service.createDisease(request);
        return ResponseEntity.ok("created success !!");
    }
    @PutMapping("{id}")
    public ResponseEntity<String> updateDisease(@PathVariable Long id,@RequestBody  DiseaseRequest request){
        service.updateDisease(id,request);
        return ResponseEntity.ok("updated success !!");
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteDisease(@PathVariable Long id){
        service.deleteDisease(id);
        return ResponseEntity.ok("deleted success !!");
    }

}
