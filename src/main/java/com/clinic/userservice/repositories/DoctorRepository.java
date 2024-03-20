package com.clinic.userservice.repositories;

import com.clinic.userservice.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> , JpaSpecificationExecutor<Doctor> {

    Page<Doctor> findBySpecialityIdOrAddress_City(Long specialityId, String phoneNumber , Pageable pageable);

}
