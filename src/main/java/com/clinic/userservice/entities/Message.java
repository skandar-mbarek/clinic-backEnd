package com.clinic.userservice.entities;

import com.clinic.userservice.enumData.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Consultation consultation;
    private String text;
    @Enumerated(EnumType.STRING)
    private Role senderRole;
    @CreationTimestamp
    private Instant createdAt;

}
