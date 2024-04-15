package com.clinic.userservice.repositories.specification;

import com.clinic.userservice.entities.Appointment;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentSpecification {
    public static Specification<Appointment> patientIdEquals(Long patientId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("patient").get("id"), patientId);
    }

    public static Specification<Appointment> statusEquals(Boolean status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Appointment> doctorFirstNameLike(String doctorFirstName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("doctor").get("firstName"), "%" + doctorFirstName + "%");
    }
    public static Specification<Appointment> doctorLastNameLike(String doctorFirstName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("doctor").get("firstName"),"%" +  doctorFirstName+ "%");
    }

    public static Specification<Appointment> yearEquals(String year) {
        return (root, query, criteriaBuilder) -> {
            int yearInt = Integer.parseInt(year);
            LocalDateTime startDate = LocalDate.of(yearInt, 1, 1).atStartOfDay();
            LocalDateTime endDate = LocalDate.of(yearInt, 12, 31).atTime(23, 59, 59);
            return criteriaBuilder.between(root.get("createdAt"), startDate, endDate);
        };
    }


    public static Specification<Appointment> buildSpecification(Long patientId, Boolean status,String doctorFirstName, String doctorLastName, String year) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (patientId != null) {
                predicates.add(patientIdEquals(patientId).toPredicate(root, query, criteriaBuilder));
            }
            if (status != null) {
                predicates.add(statusEquals(status).toPredicate(root, query, criteriaBuilder));
            }
            if (doctorFirstName != null) {
                predicates.add(doctorFirstNameLike(doctorFirstName).toPredicate(root, query, criteriaBuilder));
            }
            if (doctorLastName != null) {
                predicates.add(doctorLastNameLike(doctorLastName).toPredicate(root, query, criteriaBuilder));
            }
            if (year != null) {
                predicates.add(yearEquals(year).toPredicate(root, query, criteriaBuilder));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
