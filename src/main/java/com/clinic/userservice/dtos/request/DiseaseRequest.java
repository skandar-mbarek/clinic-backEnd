package com.clinic.userservice.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DiseaseRequest {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "name is required")
    private String description;
    @NotBlank(message = "speciality is required")
    private Long specialityId;
}
