package com.clinic.userservice.dtos.request;

import com.clinic.userservice.entities.Doctor;
import com.clinic.userservice.entities.Document;
import com.clinic.userservice.entities.Medicine;
import com.clinic.userservice.entities.Patient;
import com.clinic.userservice.enumData.Rate;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultationRequest {

    private String title;
    private String description;
    private String treatment;
    @Enumerated(EnumType.STRING)
    private Rate rate;
    private Long doctorId;
    private Long patientId;
    private Long diseaseId;
    private List<MedicineRequest> medicines;

}
