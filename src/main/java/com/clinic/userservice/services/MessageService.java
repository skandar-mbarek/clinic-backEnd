package com.clinic.userservice.services;

import com.clinic.userservice.dtos.request.MessageRequest;
import com.clinic.userservice.entities.Message;

import java.util.List;

public interface MessageService {

    Message saveMessage(MessageRequest request);
    List<Message> getAllMessageByConsultationId(Long consultationId);

}
