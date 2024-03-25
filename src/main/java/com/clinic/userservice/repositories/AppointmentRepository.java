package com.clinic.userservice.repositories;

import com.clinic.userservice.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    List<Appointment> findByDoctorUserIdAndDateAndTime(Long doctorId, LocalDate date, LocalTime time);

}
