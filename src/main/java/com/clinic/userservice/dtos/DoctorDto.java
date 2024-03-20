package com.clinic.userservice.dtos;


import com.clinic.userservice.entities.Address;
import com.clinic.userservice.entities.Doctor;
import com.clinic.userservice.entities.Schedule;
import com.clinic.userservice.entities.Speciality;
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
public class DoctorDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Gendre gendre;
    private String phoneNumber;
    private String email;
    private Date dateOfBirth;
    private String licenceNumber;
    private Date licenceDate;
    private Speciality speciality;
    private Address address;
    private Schedule schedule;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Instant createdAt;
    private Instant updatedAt;


    public static DoctorDto convertToDto(Doctor doctor){

        return DoctorDto.builder()
                .id(doctor.getUserId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLasName())
                .gendre(doctor.getGender())
                .phoneNumber(doctor.getPhoneNumber())
                .email(doctor.getEmail())
                .dateOfBirth(doctor.getDateOfBirth())
                .licenceNumber(doctor.getLicenceNumber())
                .licenceDate(doctor.getLicenceDate())
                .speciality(doctor.getSpeciality())
                .address(doctor.getAddress())
                .schedule(doctor.getSchedule())
                .role(doctor.getRole())
                .createdAt(doctor.getCreatedAt())
                .updatedAt(doctor.getUpdatedAt())
                .build();
    }

}

