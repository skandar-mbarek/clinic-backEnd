package com.clinic.userservice.services;

import com.clinic.userservice.dtos.request.AppointmentRequest;
import com.clinic.userservice.entities.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {

    public boolean isAvailable(Long doctorId, LocalDate date, LocalTime time);
    public List<String> getAvailableTimes(Long doctorId , String date);
    public void createAppointment(AppointmentRequest request);

}
