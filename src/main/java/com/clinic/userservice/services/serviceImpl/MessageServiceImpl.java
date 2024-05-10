package com.clinic.userservice.services.serviceImpl;

import com.clinic.userservice.dtos.request.MessageRequest;
import com.clinic.userservice.entities.Consultation;
import com.clinic.userservice.entities.Doctor;
import com.clinic.userservice.entities.Message;
import com.clinic.userservice.entities.Patient;
import com.clinic.userservice.repositories.MessageRepository;
import com.clinic.userservice.services.ConsultationService;
import com.clinic.userservice.services.DoctorService;
import com.clinic.userservice.services.MessageService;
import com.clinic.userservice.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {


    @Autowired
    private final MessageRepository repository;
    @Autowired
    private final ConsultationService consultationService;

    @Override
    public Message saveMessage(MessageRequest request) {

        Consultation consultation = consultationService.getConsultationById(request.getConsultationId());
        Message message = Message.builder()
                .consultation(consultation)
                .senderRole(request.getSenderRole())
                .text(request.getText())
                .build();
        return repository.save(message);


    }

    @Override
    public List<Message> getAllMessageByConsultationId(Long consultationId) {
        return repository.findAllByConsultation_Id(consultationId);
    }
}
