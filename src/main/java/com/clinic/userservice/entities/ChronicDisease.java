package com.clinic.userservice.entities;

import com.clinic.userservice.enumData.ConditionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chronic-disease")
public class ChronicDisease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ConditionType conditionType;
    private String title;
    private String description;
    @ManyToOne
    private Disease disease;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Medicine> medicines;
    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Doctor doctor;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

}
