package com.clinic.userservice.entities;

import com.clinic.userservice.enumData.Rate;
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
@Table(name = "consultation")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String title;
    private String description;
    private String treatment;
    @Enumerated(EnumType.STRING)
    private Rate rate;
    private Boolean status = Boolean.TRUE;
    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Disease disease;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Medicine> medicines;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Document> documents;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

}
