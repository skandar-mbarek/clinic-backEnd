package com.clinic.userservice.services.serviceImpl;

import com.clinic.userservice.dtos.SpecialityConsultationCount;
import com.clinic.userservice.dtos.request.ConsultationRequest;
import com.clinic.userservice.dtos.request.MedicineRequest;
import com.clinic.userservice.entities.*;
import com.clinic.userservice.entities.Document;
import com.clinic.userservice.enumData.Rate;
import com.clinic.userservice.exceptions.BadRequestException;
import com.clinic.userservice.repositories.ConsultationRepository;
import com.clinic.userservice.repositories.DiseaseRepository;
import com.clinic.userservice.repositories.DoctorRepository;
import com.clinic.userservice.repositories.PatientRepository;
import com.clinic.userservice.repositories.specification.ConsultationSpecification;
import com.clinic.userservice.services.ConsultationService;
import com.clinic.userservice.utils.FileStorage;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultationServiceImpl implements ConsultationService {

    @Autowired
    private final DoctorRepository doctorRepository;
    @Autowired
    private final PatientRepository patientRepository;
    @Autowired
    private final DiseaseRepository diseaseRepository;
    @Autowired
    private final FileStorage fileStorage;
    @Autowired
    private final ConsultationRepository repository;

    @Override
    public void createConsultation(ConsultationRequest request, List<MultipartFile> files) {
        Doctor doctor = doctorRepository.findById(request.getDoctorId()).orElseThrow(
                () -> new BadRequestException("this doctor is not exist")
        );
        Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow(
                () -> new BadRequestException("this patient is not exist")
        );
        Disease disease = null;
        if (request.getDiseaseId() != null) {
            disease = diseaseRepository.findById(request.getDiseaseId()).orElseThrow(
                    () -> new BadRequestException("this disease is not exist")
            );
        }

        List<Document> documents = new ArrayList<>();
        List<Medicine> medicines = new ArrayList<>();

        if (files != null) {
            for (MultipartFile file : files) {
                Document document = Document.builder()
                        .nameId(fileStorage.storeFile(file))
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .build();
                documents.add(document);
            }
        }
        if (request.getMedicines() != null) {
            for (MedicineRequest medicineRequest : request.getMedicines()) {
                Medicine medicine = Medicine.builder()
                        .name(medicineRequest.getName())
                        .description(medicineRequest.getDescription())
                        .build();
                medicines.add(medicine);
            }
        }

        Consultation consultation = Consultation.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .treatment(request.getTreatment())
                .rate(request.getRate())
                .status(Boolean.TRUE)
                .disease(disease)
                .doctor(doctor)
                .patient(patient)
                .medicines(medicines)
                .documents(documents)
                .build();

        repository.save(consultation);

    }

    @Override
    public Page<Consultation> searchConsultation(
            Long patientId,
            Long doctorId,
            Rate rate,
            String year,
            Pageable pageable
    ) {
        Specification<Consultation> spec = ConsultationSpecification.buildSpecification(
                patientId, doctorId, year, rate
        );
        return repository.findAll(spec, pageable);
    }

    @Override
    public List<Doctor> getDoctorByPatient(Long patientId) {
        return repository.findDistinctDoctorByPatientUserId(patientId);
    }



    @Override
    public byte[] generatePrescription(Long consultationId) {


        Consultation consultation = getConsultationById(consultationId);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A5);

        try {
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();

            //Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD,16,BaseColor.BLUE);
            Paragraph title = new Paragraph("Ordonnance" , titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            //doctor
            Font doctorFont = FontFactory.getFont(FontFactory.TIMES_ROMAN,12,Font.BOLDITALIC,BaseColor.BLUE);
            Paragraph doctor = new Paragraph("Dr. "+consultation.getDoctor().getLastName()+" "+consultation.getDoctor().getFirstName(),doctorFont);
            doctor.setAlignment(Element.ALIGN_LEFT);
            doctor.setSpacingBefore(10);
            document.add(doctor);
            //Patient
            Font patientFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            Paragraph patient = new Paragraph("Patient(e) :   " + consultation.getPatient().getLastName()+" "+consultation.getPatient().getFirstName(), patientFont);
            patient.setAlignment(Element.ALIGN_CENTER);
            patient.setSpacingBefore(10);
            document.add(patient);
            //date
            LocalDateTime date = LocalDateTime.ofInstant(consultation.getCreatedAt(), ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = date.format(formatter);

            Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
            Paragraph prescriptionDate = new Paragraph("Date: " + formattedDate, dateFont);
            prescriptionDate.setAlignment(Element.ALIGN_CENTER);
            prescriptionDate.setSpacingBefore(5);
            prescriptionDate.setSpacingAfter(20);
            document.add(prescriptionDate);

            //Medicines
            for (Medicine medicine : consultation.getMedicines()) {
                Font medicineFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.BLACK);
                Paragraph medicineDetails = new Paragraph("- " + medicine.getName() + " : " + medicine.getDescription(), medicineFont);
                medicineDetails.setAlignment(Element.ALIGN_LEFT);
                medicineDetails.setSpacingBefore(10);
                document.add(medicineDetails);
            }
            //footer
            Font locationFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, BaseColor.BLUE);
            Chunk locationText = getChunk(consultation, locationFont);
            Paragraph location = new Paragraph(locationText);
            location.setAlignment(Element.ALIGN_CENTER);

            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(90);
            table.setTotalWidth(document.getPageSize().getWidth());
            table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(location);

            PdfContentByte canvas = writer.getDirectContent();
            table.writeSelectedRows(0, -1, 20, document.bottom() + table.getTotalHeight(), canvas);



            document.close();
            writer.close(); //

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();

    }

    private static Chunk getChunk(Consultation consultation, Font locationFont) {
        Chunk locationText = new Chunk("Dr. "+ consultation.getDoctor().getLastName()+
                " "+ consultation.getDoctor().getFirstName()+
                " | "+ consultation.getDoctor().getAddress().getCountry()+
                " | "+ consultation.getDoctor().getAddress().getState()+
                " | "+ consultation.getDoctor().getAddress().getCity()+
                " | "+ consultation.getDoctor().getAddress().getStreet()+
                " | "+ consultation.getDoctor().getAddress().getZipCode()+
                " | Numéro : "+ consultation.getDoctor().getPhoneNumber()+
                " | Email : "+ consultation.getDoctor().getEmail()
                , locationFont);
        locationText.setUnderline(0.1f, -2f); // Underline with thickness and position
        locationText.setBackground(BaseColor.WHITE); // White background for better visibility of underline
        return locationText;
    }

    @Override
    public Consultation getConsultationById(Long consultationId) {
        return repository.findById(consultationId).orElseThrow(
                ()->new BadRequestException("this consultation is not exist")
        );
    }

    @Override
    public List<SpecialityConsultationCount> getConsultationCountBySpeciality(Long patientId) {
        return repository.countConsultationsBySpeciality(patientId);
    }

    @Override
    public Map<String, Object> getConsultationCountByMonth(String year, Long patientId) {
        List<Object[]> results = repository.countConsultationsByMonth(year, patientId);

        // Initialize a map with all months and set counts to 0
        Map<Integer, Long> monthCountMap = IntStream.rangeClosed(1, 12)
                .boxed()
                .collect(Collectors.toMap(i -> i, i -> 0L));

        // Fill the map with actual results
        for (Object[] result : results) {
            Integer month = (Integer) result[0];
            Long count = (Long) result[1];
            monthCountMap.put(month, count);
        }

        // Create labels and data arrays
        String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        Long[] data = new Long[12];

        for (int i = 0; i < 12; i++) {
            data[i] = monthCountMap.get(i + 1);
        }

        // Prepare the result in the required format
        Map<String, Object> result = new HashMap<>();
        result.put("labels", labels);
        result.put("datasets", List.of(Map.of("data", data)));

        return result;
    }


}
