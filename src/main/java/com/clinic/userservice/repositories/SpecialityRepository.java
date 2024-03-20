package com.clinic.userservice.repositories;

import com.clinic.userservice.entities.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialityRepository extends JpaRepository<Speciality,Long> {
}
