package com.clinic.userservice.services.serviceImpl;

import com.clinic.userservice.dtos.DoctorDto;
import com.clinic.userservice.entities.Doctor;
import com.clinic.userservice.exceptions.BadRequestException;
import com.clinic.userservice.repositories.DoctorRepository;
import com.clinic.userservice.repositories.specification.DoctorSpecification;
import com.clinic.userservice.services.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {


    @Autowired
    private final DoctorRepository repository;

    @Override
    public Page<DoctorDto> searchDoctors(Long specialityId, String state, int page, int size) {

        PageRequest pageable = PageRequest.of(page, size);

        Specification<Doctor> spec = DoctorSpecification.findBySpecialityIdAndState(specialityId, state);
        Page<Doctor> doctorPage = repository.findAll(spec, pageable);

        return doctorPage.map(DoctorDto::convertToDto);
    }

    @Override
    public DoctorDto getById(Long id) {
        Doctor doctor = repository.findById(id).orElseThrow(
                () -> new BadRequestException("this doctor is not exist")
        );
        return DoctorDto.convertToDto(doctor);
    }

    @Override
    public Doctor getDoctorById(Long doctorId) {
        return repository.findById(doctorId).orElseThrow(
                () -> new BadRequestException("this doctor is not exist")
        );
    }

}


