package com.clinic.userservice.services.serviceImpl;

import com.clinic.userservice.dtos.request.SpecialityRequest;
import com.clinic.userservice.entities.Speciality;
import com.clinic.userservice.exceptions.BadRequestException;
import com.clinic.userservice.repositories.SpecialityRepository;
import com.clinic.userservice.services.SpecialityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpecialityServiceImpl implements SpecialityService {

    @Autowired
    private final SpecialityRepository repository;


    @Override
    public void create(SpecialityRequest specialityRequest) {

        Speciality speciality = Speciality.builder()
                .name(specialityRequest.getName())
                .description(specialityRequest.getDescription())
                .build();

        repository.save(speciality);
    }

    @Override
    public List<Speciality> getAll() {
        return repository.findAll();
    }

    @Override
    public Speciality getById(Long id) {
        return repository.findById(id).orElseThrow(
                ()->new BadRequestException("this speciality is not exist")
         );
    }
}
