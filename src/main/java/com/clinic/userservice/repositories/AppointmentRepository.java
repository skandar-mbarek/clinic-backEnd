package com.clinic.userservice.repositories;

import com.clinic.userservice.entities.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long>, JpaSpecificationExecutor<Appointment> {

    List<Appointment> findByDoctorUserIdAndDateAndTime(Long doctorId, LocalDate date, LocalTime time);
    boolean existsByDateAndTimeAndDoctorUserId(LocalDate date,LocalTime time,Long doctorId);
    Page<Appointment> findAll(@NonNull Specification<Appointment> specification,@NonNull Pageable pageable);
    List<Appointment> findByDateBetween(LocalDate currentDate,LocalDate twoDaysLater);

    @Query("SELECT a FROM Appointment a " +
            "WHERE a.patient.userId = :patientId " +
            "AND a.status = true " +
            "AND (a.date > :currentDate OR (a.date = :currentDate AND a.time > :currentTime))")
    List<Appointment> findUpcomingAppointmentsByPatientId(@Param("patientId") Long patientId,
                                                          @Param("currentDate") LocalDate currentDate,
                                                          @Param("currentTime") LocalTime currentTime);
}
