package com.clinic.userservice.controllers.patientControllers;

import com.clinic.userservice.constants.Constants;
import com.clinic.userservice.entities.Consultation;
import com.clinic.userservice.entities.Doctor;
import com.clinic.userservice.enumData.Rate;
import com.clinic.userservice.services.ConsultationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APP_ROOT_PATIENT+"/consultation")
@CrossOrigin("*")
@Tag(name = "Consultation Management")
public class ConsultationPatientController {

    @Autowired
    private final ConsultationService service;

    @Operation(summary = "Get Consultations By Filter")
    @GetMapping("{patientId}")
    public ResponseEntity<Page<Consultation>> getAllConsultations(
            @PathVariable Long patientId,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) String year,
            @RequestParam(required = false)Rate rate,
            Pageable pageable){

        Page<Consultation> consultations = service.searchConsultation(patientId,doctorId,rate,year,pageable);
        return ResponseEntity.ok(consultations);
    }

    @Operation(summary = "Get Doctors Who Conducted Consultations With The Patient")
    @GetMapping("doctors/{patientId}")
    public ResponseEntity<List<Doctor>> getDoctorsByPatient(@PathVariable Long patientId){

        return ResponseEntity.ok(service.getDoctorByPatient(patientId));
    }
    @Operation(summary = "Get Prescription")
    @GetMapping("/generate-Prescription/{consultationId}")
    public ResponseEntity<byte[]> generatePrescription(@PathVariable Long consultationId) {


        // Set medication details, other information, etc.

        // Generate the PDF
        byte[] pdfBytes = service.generatePrescription(consultationId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "Prescription.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}
