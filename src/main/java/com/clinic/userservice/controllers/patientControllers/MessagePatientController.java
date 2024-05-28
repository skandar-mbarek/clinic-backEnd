package com.clinic.userservice.controllers.patientControllers;

import com.clinic.userservice.constants.Constants;
import com.clinic.userservice.entities.Message;
import com.clinic.userservice.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APP_ROOT_PATIENT +"/message")
@CrossOrigin("*")
@Tag(name = "Messages Management")
public class MessagePatientController {

    @Autowired
    private final MessageService service;

    @Operation(summary = "Get Messages By Consultation ID")
    @GetMapping("{consultationId}")
    public ResponseEntity<List<Message>> getMessagesByConsultation(@PathVariable Long consultationId){
        return ResponseEntity.ok(service.getAllMessageByConsultationId(consultationId));
    }

}
