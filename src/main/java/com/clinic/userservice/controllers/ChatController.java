package com.clinic.userservice.controllers;

import com.clinic.userservice.dtos.request.MessageRequest;
import com.clinic.userservice.entities.Consultation;
import com.clinic.userservice.entities.Message;
import com.clinic.userservice.services.ConsultationService;
import com.clinic.userservice.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    @Autowired
    private final MessageService messageService;
    @Autowired
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    private final ConsultationService consultationService;

    @MessageMapping("/send-message")
    public void sendMessage(@Payload MessageRequest message){
        Message savedMessage = messageService.saveMessage(message);
        Consultation consultation = consultationService.getConsultationById(message.getConsultationId());

        messagingTemplate.convertAndSendToUser(
                String.valueOf(consultation.getDoctor().getUserId()),
                "/topic/consultation/" + consultation.getId() + "/messages",
                savedMessage
        );
        messagingTemplate.convertAndSendToUser(
                String.valueOf(consultation.getPatient().getUserId()),
                "/topic/consultation/" + consultation.getId() + "/messages",
                savedMessage
        );

    }

}
