package com.clinic.userservice.dtos.request;

import com.clinic.userservice.enumData.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageRequest {


    @NotNull(message = "consultation id is required")
    private Long consultationId;
    @Enumerated(EnumType.STRING)
    private Role senderRole;
    @NotBlank(message = "text is required")
    private String text;

}
