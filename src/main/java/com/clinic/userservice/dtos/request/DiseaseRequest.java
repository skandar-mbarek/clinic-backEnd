package com.clinic.userservice.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DiseaseRequest {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "name is required")
    private String description;
    @NotNull(message = "speciality is required")
    private Long specialityId;
}
