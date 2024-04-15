package com.clinic.userservice.dtos.request;

import com.clinic.userservice.entities.Address;
import com.clinic.userservice.enumData.Gendre;
import com.clinic.userservice.enumData.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
public class UpdatePatientRequest {
    private String firstName;
    private String lastName;
    private Gendre gender;
    private String email;
    private String imageName;
    private Date dateOfBirth;
    private AddressRequest address;
    private String cnamCode;



}
