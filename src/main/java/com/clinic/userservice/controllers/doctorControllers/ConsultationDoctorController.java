package com.clinic.userservice.controllers.doctorControllers;

import com.clinic.userservice.constants.Constants;
import com.clinic.userservice.dtos.request.ConsultationRequest;
import com.clinic.userservice.services.ConsultationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APP_ROOT_DOCTOR +"/consultation")
@CrossOrigin("*")
@Tag(name = "Consultation Management")
public class ConsultationDoctorController {

    @Autowired
    private final ConsultationService service;


    @Operation(summary = "Create Consultation")
    @PostMapping
    public ResponseEntity<String> createConsultation(

            ConsultationRequest request,
            @RequestParam(required = false) List<MultipartFile> files
    ){
        service.createConsultation(request,files);
        return ResponseEntity.ok("save success !!");
    }

}
