package com.clinic.userservice.services;

import com.clinic.userservice.dtos.request.AppointmentRequest;
import com.clinic.userservice.entities.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {

    boolean isAvailable(Long doctorId, LocalDate date, LocalTime time);
    List<String> getAvailableTimes(Long doctorId , String date);
    void createAppointment(AppointmentRequest request);

    Page<Appointment> searchAppointmentsByPatientId(Long patientId, Boolean status, String doctorFirstName,String doctorLastName, String year, Sort.Direction sortDirection, Pageable pageable);

    void sendSmsForUpcomingAppointments();
    void cancelAppointment(Long appointmentId);
}
