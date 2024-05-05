package com.clinic.userservice.controllers.patientControllers;

import com.clinic.userservice.constants.Constants;
import com.clinic.userservice.dtos.request.AppointmentRequest;
import com.clinic.userservice.entities.Appointment;
import com.clinic.userservice.services.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.APP_ROOT_PATIENT +"/appointment")
@CrossOrigin("*")
@Tag(name = "Appointment Management")
public class AppointmentPatientController {

    @Autowired
    private final AppointmentService service;

    @Operation(summary = "Get Available Appointment Times")
    @GetMapping("times/{doctorId}")
    public ResponseEntity<List<String>> getAvailableAppointmentTimes(
            @PathVariable Long doctorId,
            @RequestParam("date")
            @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in the format yyyy-MM-dd")
            String date
            )
    {
        return ResponseEntity.ok(service.getAvailableTimes(doctorId, date));
    }
    @Operation(summary = "Get Appointments By Filter")
    @GetMapping("{patientId}")
    public ResponseEntity<Page<Appointment>> getAllAppointmentsByPatientId(
            @PathVariable Long patientId,
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) String doctorFirstName,
            @RequestParam(required = false) String doctorLastName,
            @RequestParam(required = false) String year,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            Pageable pageable) {

        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Page<Appointment> appointments = service.searchAppointmentsByPatientId(patientId, status, doctorFirstName,doctorLastName, year, direction, pageable);
        return ResponseEntity.ok(appointments);
    }
    @Operation(summary = "Take Appointment")
    @PostMapping
    public ResponseEntity<String> createAppointment(@RequestBody @Valid AppointmentRequest request){
        service.createAppointment(request);
        return ResponseEntity.ok("create success !");
    }
    @Operation(summary = "Cancel Appointment")
    @PostMapping("{appointmentId}")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long appointmentId){
        service.cancelAppointment(appointmentId);
        return ResponseEntity.ok("cancelled success !");
    }
}
