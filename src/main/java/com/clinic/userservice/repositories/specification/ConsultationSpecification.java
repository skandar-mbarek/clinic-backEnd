package com.clinic.userservice.repositories.specification;

import com.clinic.userservice.entities.Appointment;
import com.clinic.userservice.entities.Consultation;
import com.clinic.userservice.enumData.Rate;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultationSpecification {
    public static Specification<Consultation> patientIdEquals(Long patientId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("patient").get("id"), patientId);
    }

    public static Specification<Consultation> doctorIdEquals(Long doctorId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("doctor").get("id"),  + doctorId);
    }
    public static Specification<Consultation> yearEquals(String year) {
        return (root, query, criteriaBuilder) -> {
            int yearInt = Integer.parseInt(year);
            LocalDateTime startDate = LocalDate.of(yearInt, 1, 1).atStartOfDay();
            LocalDateTime endDate = LocalDate.of(yearInt, 12, 31).atTime(23, 59, 59);
            return criteriaBuilder.between(root.get("createdAt"), startDate, endDate);
        };
    }
    public static Specification<Consultation> rateEquals(Rate rate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("rate"),rate );
    }


    public static Specification<Consultation> buildSpecification(Long patientId,Long doctorId,String year, Rate rate){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (patientId != null) {
                predicates.add(patientIdEquals(patientId).toPredicate(root, query, criteriaBuilder));
            }
            if (doctorId != null) {
                predicates.add(doctorIdEquals(doctorId).toPredicate(root, query, criteriaBuilder));
            }

            if (rate != null) {
                predicates.add(rateEquals(rate).toPredicate(root, query, criteriaBuilder));
            }
            if (year != null) {
                predicates.add(yearEquals(year).toPredicate(root, query, criteriaBuilder));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
