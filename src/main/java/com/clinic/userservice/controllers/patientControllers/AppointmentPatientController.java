package com.clinic.userservice.controllers.patientControllers;

import com.clinic.userservice.constants.Constants;
import com.clinic.userservice.dtos.request.AppointmentRequest;
import com.clinic.userservice.entities.Appointment;
import com.clinic.userservice.services.AppointmentService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APP_ROOT_PATIENT +"/appointment")
@CrossOrigin("*")
public class AppointmentPatientController {

    @Autowired
    private final AppointmentService service;

    @GetMapping("{doctorId}")
    public ResponseEntity<List<String>> getAvailableAppointmentTimes(
            @PathVariable Long doctorId,
            @RequestParam("date")
            @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in the format yyyy-MM-dd")
            String date
            )
    {
        return ResponseEntity.ok(service.getAvailableTimes(doctorId, date));
    }
    @PostMapping
    public ResponseEntity<String> createAppointment(@RequestBody AppointmentRequest request){
        service.createAppointment(request);
        return ResponseEntity.ok("create success !");
    }
}
