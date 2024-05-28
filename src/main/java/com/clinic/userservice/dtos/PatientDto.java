package com.clinic.userservice.dtos;

import com.clinic.userservice.entities.Address;
import com.clinic.userservice.entities.Patient;
import com.clinic.userservice.enumData.Gendre;
import com.clinic.userservice.enumData.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private Gendre gender;
    private String phoneNumber;
    private String email;
    private String imageName;
    private Date dateOfBirth;
    private Address address;
    private String cnamCode;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Instant createdAt;
    private Instant updatedAt;


    public static PatientDto convertToDto(Patient patient){

        return PatientDto.builder()
                .userId(patient.getUserId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .gender(patient.getGender())
                .phoneNumber(patient.getPhoneNumber())
                .email(patient.getEmail())
                .imageName(patient.getImageName())
                .dateOfBirth(patient.getDateOfBirth())
                .address(patient.getAddress())
                .cnamCode(patient.getCnamCode())
                .role(patient.getRole())
                .createdAt(patient.getCreatedAt())
                .updatedAt(patient.getUpdatedAt())
                .build();
    }
}
