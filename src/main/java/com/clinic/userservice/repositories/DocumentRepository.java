package com.clinic.userservice.repositories;

import com.clinic.userservice.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document,String> {
}
