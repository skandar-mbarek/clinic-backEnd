package com.clinic.userservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "doctor")
public class Doctor extends User {


    private String licenceNumber;
    private Date licenceDate;
    @ManyToOne
    private Speciality speciality;
    @OneToOne
    private Schedule schedule;

}
