package com.clinic.userservice.controllers.patientControllers;

import com.clinic.userservice.constants.Constants;
import com.clinic.userservice.dtos.DoctorDto;
import com.clinic.userservice.dtos.PatientDto;
import com.clinic.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APP_ROOT_PATIENT)
@CrossOrigin("*")
public class PatientController {


    @Autowired
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<PatientDto> getMe (){
        return ResponseEntity.ok(userService.getMe());
    }
}
