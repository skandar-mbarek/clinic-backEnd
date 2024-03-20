package com.clinic.userservice.repositories.specification;

import com.clinic.userservice.entities.Doctor;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;


public class DoctorSpecification {

    public static Specification<Doctor> findBySpecialityIdAndCity(Long specialityId ,String city){
        return (Root<Doctor> root, CriteriaQuery<?> query , CriteriaBuilder criteriaBuilder)->{
            Predicate predicate = criteriaBuilder.conjunction();

            if (specialityId != null){
                predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("speciality").get("id"),specialityId));
            }
            if (city != null && !city.isEmpty()){
                predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("address").get("city"),city));
            }
            return predicate;
        };
    }

}
