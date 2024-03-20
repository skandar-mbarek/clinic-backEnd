package com.clinic.userservice.controllers.patientControllers;


import com.clinic.userservice.dtos.DoctorDto;
import com.clinic.userservice.services.DoctorService;
import com.clinic.userservice.constants.Constants;
import com.twilio.base.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APP_ROOT_PATIENT +"/doctor")
@CrossOrigin("*")
public class DoctorPatientController {

    @Autowired
    private DoctorService doctorService;


    @GetMapping
    public ResponseEntity<Page<DoctorDto>> searchDoctor(
            @RequestParam(name = "specialityId",required = false) Long specialityId,
            @RequestParam(name = "city",required = false) String city,
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "10") int size
    ){

        return new ResponseEntity(doctorService.searchDoctors(specialityId, city, page, size), HttpStatus.OK);

    }
    @GetMapping("{id}")
    public ResponseEntity<DoctorDto> getDoctorById (@PathVariable Long id){
        return ResponseEntity.ok(doctorService.getById(id));
    }



}
