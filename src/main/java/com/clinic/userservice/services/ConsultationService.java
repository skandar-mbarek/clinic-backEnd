package com.clinic.userservice.services;

import com.clinic.userservice.dtos.SpecialityConsultationCount;
import com.clinic.userservice.dtos.request.ConsultationRequest;
import com.clinic.userservice.entities.Consultation;
import com.clinic.userservice.entities.Doctor;
import com.clinic.userservice.enumData.Rate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ConsultationService {

    public void createConsultation(ConsultationRequest request, List<MultipartFile> files);

    Page<Consultation> searchConsultation(Long patientId, Long doctorId, Rate rate, String year, Pageable pageable);

    List<Doctor> getDoctorByPatient(Long patientId);

    byte[] generatePrescription(Long consultationId);
    Consultation getConsultationById(Long ConsultationId);
    public List<SpecialityConsultationCount> getConsultationCountBySpeciality(Long patientId);
    public Map<String, Object> getConsultationCountByMonth(String year, Long patientId);

}
