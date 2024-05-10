package com.clinic.userservice.repositories;

import com.clinic.userservice.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    List<Message> findAllByConsultation_Id(Long consultationId);

}
