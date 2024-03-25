package com.clinic.userservice.services.serviceImpl;

import com.clinic.userservice.dtos.DoctorDto;
import com.clinic.userservice.dtos.request.AppointmentRequest;
import com.clinic.userservice.entities.*;
import com.clinic.userservice.exceptions.BadRequestException;
import com.clinic.userservice.repositories.AppointmentRepository;
import com.clinic.userservice.repositories.DiseaseRepository;
import com.clinic.userservice.repositories.DoctorRepository;
import com.clinic.userservice.repositories.PatientRepository;
import com.clinic.userservice.services.AppointmentService;
import com.clinic.userservice.services.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private final AppointmentRepository repository;
    @Autowired
    private final DoctorService doctorService;
    @Autowired
    private final DoctorRepository doctorRepository;
    @Autowired
    private final PatientRepository patientRepository;
    @Autowired
    private final DiseaseRepository diseaseRepository;

    @Override
    public boolean isAvailable(Long doctorId, LocalDate date, LocalTime time) {
        List<Appointment> appointments = repository.findByDoctorUserIdAndDateAndTime(doctorId,date,time);
        return appointments.isEmpty();
    }

    @Override
    public List<String> getAvailableTimes(Long doctorId, String dateString) {

        LocalDate date = LocalDate.parse(dateString);

        Schedule schedule = doctorService.getById(doctorId).getSchedule();
        List<String> availableTimes = new ArrayList<>();

        LocalTime time = schedule.getTimeFrom();

        while (time.isBefore(schedule.getTimeTo())){
            if (isAvailable(doctorId,date,time)){
                availableTimes.add(time.format(DateTimeFormatter.ofPattern("HH:mm")));
            }
            time = time.plusMinutes(schedule.getConsultationDuration());
        }
        return availableTimes;

    }

    @Override
    public void createAppointment(AppointmentRequest request) {

        Doctor doctor = doctorRepository.findById(request.getDoctorId()).orElseThrow(
                ()->new BadRequestException("this doctor is not exist")
        );
        Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow(
                ()->new BadRequestException("this patient is not exist")
        );
        Disease disease = diseaseRepository.findById(request.getDiseaseId()).orElseThrow(
                ()->new BadRequestException("this disease is not exist")
        );

        LocalDate date = LocalDate.parse(request.getDate());
        LocalTime time = LocalTime.parse(request.getTime());

        Appointment appointment = Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .disease(disease)
                .date(date)
                .time(time)
                .status(Boolean.TRUE)
                .build();

        repository.save(appointment);
    }
}
