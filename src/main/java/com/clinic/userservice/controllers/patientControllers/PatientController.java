package com.clinic.userservice.controllers.patientControllers;

import com.clinic.userservice.constants.Constants;
import com.clinic.userservice.dtos.DoctorDto;
import com.clinic.userservice.dtos.PatientDto;
import com.clinic.userservice.dtos.request.UpdatePatientRequest;
import com.clinic.userservice.services.PatientService;
import com.clinic.userservice.services.UserService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APP_ROOT_PATIENT)
@CrossOrigin("*")
public class PatientController {


    @Autowired
    private final UserService userService;
    @Autowired
    private final PatientService service;

    @GetMapping("/me")
    public ResponseEntity<PatientDto> getMe (){
        return ResponseEntity.ok(userService.getMe());
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updatePatient(@PathVariable(required = true) Long id,
                                                @RequestBody(required = false) UpdatePatientRequest request){

        service.updatePatient(id,request);
        return ResponseEntity.ok("updated success !");
    }
}
