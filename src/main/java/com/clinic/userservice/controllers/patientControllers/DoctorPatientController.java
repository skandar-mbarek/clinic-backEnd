package com.clinic.userservice.controllers.patientControllers;


import com.clinic.userservice.dtos.DoctorDto;
import com.clinic.userservice.services.DoctorService;
import com.clinic.userservice.constants.Constants;
import com.twilio.base.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APP_ROOT_PATIENT +"/doctor")
@CrossOrigin("*")
@Tag(name = "Doctor Management")
public class DoctorPatientController {

    @Autowired
    private DoctorService doctorService;


    @Operation(summary = "Get Doctors By Filter")
    @GetMapping
    public ResponseEntity<Page<DoctorDto>> searchDoctor(
            @RequestParam(name = "specialityId",required = false) Long specialityId,
            @RequestParam(name = "state",required = false) String state,
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "10") int size
    ){

        return new ResponseEntity(doctorService.searchDoctors(specialityId, state, page, size), HttpStatus.OK);

    }
    @Operation(summary = "Get Doctor By Id")
    @GetMapping("{id}")
    public ResponseEntity<DoctorDto> getDoctorById (@PathVariable Long id){
        return ResponseEntity.ok(doctorService.getById(id));
    }



}
