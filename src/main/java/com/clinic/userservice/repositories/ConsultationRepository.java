package com.clinic.userservice.repositories;

import com.clinic.userservice.dtos.SpecialityConsultationCount;
import com.clinic.userservice.entities.Consultation;
import com.clinic.userservice.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.print.Doc;
import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation,Long>, JpaSpecificationExecutor<Consultation> {

    @Query("SELECT DISTINCT c.doctor from Consultation c where c.patient.userId = :patientId")
    List<Doctor> findDistinctDoctorByPatientUserId(Long patientId);

    @Query("SELECT new com.clinic.userservice.dtos.SpecialityConsultationCount(s.name,COUNT(c))" +
            "FROM Consultation c " +
            "JOIN c.doctor d " +
            "JOIN d.speciality s " +
            "WHERE c.patient.userId = :patientId " +
            "GROUP BY s.name"
    )
    List<SpecialityConsultationCount> countConsultationsBySpeciality(@Param("patientId") Long patientId);

    @Query("SELECT MONTH(c.createdAt) as month, COUNT(c) as count " +
            "FROM Consultation c " +
            "WHERE YEAR(c.createdAt) = :year AND c.patient.userId = :patientId " +
            "GROUP BY MONTH(c.createdAt) " +
            "ORDER BY MONTH(c.createdAt)")
    List<Object[]> countConsultationsByMonth(@Param("year") String year, @Param("patientId") Long patientId);

}
