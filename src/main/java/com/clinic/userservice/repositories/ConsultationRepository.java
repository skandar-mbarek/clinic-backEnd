package com.clinic.userservice.repositories;

import com.clinic.userservice.entities.Consultation;
import com.clinic.userservice.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.print.Doc;
import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation,Long>, JpaSpecificationExecutor<Consultation> {

    @Query("SELECT DISTINCT c.doctor from Consultation c where c.patient.userId = :patientId")
    List<Doctor> findDistinctDoctorByPatientUserId(Long patientId);

}
