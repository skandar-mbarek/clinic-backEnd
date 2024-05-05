package com.clinic.userservice.dtos.request;

import com.clinic.userservice.enumData.Gendre;
import com.clinic.userservice.enumData.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientRegisterRequest {
    private String firstName;
    private String lasName;
    @Enumerated(EnumType.STRING)
    private Gendre gendre;
    private String phoneNumber;
    private String password;
    private Date dateOfBirth;
    private AddressRequest address;
    @Enumerated(EnumType.STRING)
    private Role role;

}
