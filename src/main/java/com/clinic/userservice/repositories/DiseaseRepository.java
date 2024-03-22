package com.clinic.userservice.repositories;


import com.clinic.userservice.entities.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    List<Disease> findDiseasesBySpecialityId(Long id);
}
