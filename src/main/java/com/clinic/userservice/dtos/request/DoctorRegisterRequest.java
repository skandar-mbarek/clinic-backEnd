package com.clinic.userservice.dtos.request;

import com.clinic.userservice.enumData.Gendre;
import com.clinic.userservice.enumData.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorRegisterRequest {

    private String firstName;
    private String lasName;
    @Enumerated(EnumType.STRING)
    private Gendre gendre;
    private String phoneNumber;
    private String email;
    private String password;
    private Date dateOfBirth;
    private String licenceNumber;
    private Date licenceDate;
    private Long specialityId;
    private AddressRequest address;
    private ScheduleRequest schedule;
    @Enumerated(EnumType.STRING)
    private Role role;

}
