package com.clinic.userservice.dtos.request;

import com.clinic.userservice.enumData.State;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequest {

    @Enumerated(EnumType.STRING)
    private State state;
    private String city;
    private String country;
    private String street;
    private Long zipCode;

}
