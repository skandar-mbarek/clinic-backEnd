package com.clinic.userservice.dtos.request;

import com.clinic.userservice.enumData.ConditionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChronicDiseaseRequest {

    private ConditionType conditionType;
    private String title;
    private String description;
    private Long doctorId;
    private Long patientId;
    private Long diseaseId;
    private List<MedicineRequest> medicines;

}
