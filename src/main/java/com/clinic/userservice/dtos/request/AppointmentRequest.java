package com.clinic.userservice.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NonNull;

@Data
public class AppointmentRequest {

    @NotNull(message = "the doctor is required")
    private Long doctorId;
    @NotNull(message = "the patient is required")
    private Long patientId;
    private Long DiseaseId;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in the format yyyy-MM-dd")
    private String date;
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "Time must be in the format HH:mm")
    private String time;
    private Boolean status;

}
