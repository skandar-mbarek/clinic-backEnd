package com.clinic.userservice.services.serviceImpl;

import com.clinic.userservice.dtos.request.AppointmentRequest;
import com.clinic.userservice.dtos.request.SmsRequest;
import com.clinic.userservice.entities.*;
import com.clinic.userservice.exceptions.BadRequestException;
import com.clinic.userservice.repositories.AppointmentRepository;
import com.clinic.userservice.repositories.DiseaseRepository;
import com.clinic.userservice.repositories.DoctorRepository;
import com.clinic.userservice.repositories.PatientRepository;
import com.clinic.userservice.repositories.specification.AppointmentSpecification;
import com.clinic.userservice.services.AppointmentService;
import com.clinic.userservice.services.DoctorService;
import com.clinic.userservice.utils.SmsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
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
    @Autowired
    private final SmsUtil smsUtil;

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
        Disease disease = null;
        if(request.getDiseaseId()!=null){
            disease = diseaseRepository.findById(request.getDiseaseId()).orElseThrow(
                    ()->new BadRequestException("this disease is not exist")
            );
        }

        LocalDate date = LocalDate.parse(request.getDate());
        LocalTime time = LocalTime.parse(request.getTime());

        boolean exists = repository.existsByDateAndTimeAndDoctorUserId(date,time,doctor.getUserId());

        if (exists){
            throw new BadRequestException("Sorry , this time is all ready booked");
        }

        Appointment appointment = Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .disease(disease)
                .description(request.getDescription())
                .date(date)
                .time(time)
                .status(Boolean.TRUE)
                .build();

        repository.save(appointment);
    }

    @Override
    public Page<Appointment> searchAppointmentsByPatientId(Long patientId, Boolean status, String doctorFirstName, String doctorLastName, String year, Sort.Direction sortDirection, Pageable pageable) {
        Specification<Appointment> spec = AppointmentSpecification.buildSpecification(
                patientId,status,doctorFirstName,doctorLastName,year
        );
        if (sortDirection != null){
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),Sort.by(sortDirection,"createdAt"));
        }
        return repository.findAll(spec,pageable);
    }

    @Override
    //@Scheduled(fixedRate = 86400000) //86400000 = 24H
    public void sendSmsForUpcomingAppointments() {


        LocalDate currentDate = LocalDate.now();
        LocalDate twoDaysLater = currentDate.plusDays(2);
        List<Appointment> upComingAppointment = repository.findByDateBetween(currentDate,twoDaysLater);

        for(Appointment appointment : upComingAppointment){
            String msg = String.format("You have an appointment with Doctor %s at %s on %s.",
                    appointment.getDoctor().getFirstName(),
                    appointment.getDate(),
                    appointment.getTime());
            SmsRequest sms = new SmsRequest(appointment.getPatient().getPhoneNumber(),msg);
            smsUtil.sendSms(sms);
        }
    }

    @Override
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = repository.findById(appointmentId).orElseThrow(
                ()->new BadRequestException("This appointment is not exist")
        );
        appointment.setStatus(false);
        repository.save(appointment);
    }

    @Override
    public List<Appointment> getUpcomingAppointmentsByPatientId(Long patientId) {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        return repository.findUpcomingAppointmentsByPatientId(patientId, currentDate, currentTime);
    }

}
