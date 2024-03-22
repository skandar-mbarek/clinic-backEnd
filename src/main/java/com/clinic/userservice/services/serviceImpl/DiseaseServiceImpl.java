package com.clinic.userservice.services.serviceImpl;

import com.clinic.userservice.dtos.request.DiseaseRequest;
import com.clinic.userservice.entities.Disease;
import com.clinic.userservice.entities.Speciality;
import com.clinic.userservice.exceptions.BadRequestException;
import com.clinic.userservice.repositories.DiseaseRepository;
import com.clinic.userservice.repositories.SpecialityRepository;
import com.clinic.userservice.services.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiseaseServiceImpl implements DiseaseService {

    @Autowired
    private final DiseaseRepository repository;
    @Autowired
    private final SpecialityRepository specialityRepository;

    @Override
    public Disease getDiseaseById(Long id) {

        return repository.findById(id).orElseThrow(
                () -> new BadRequestException("this Disease is not exist")
        );
    }

    @Override
    public List<Disease> getDiseasesBySpeciality(Long specialityId) {

        if (specialityRepository.existsById(specialityId)) {

            return repository.findDiseasesBySpecialityId(specialityId);
        }
        throw new BadRequestException("this speciality is not exist");
    }

    @Override
    public void createDisease(DiseaseRequest request) {

        Speciality speciality = specialityRepository.findById(request.getSpecialityId()).orElseThrow(
                () -> new BadRequestException("this speciality is not exist")
        );

        Disease disease = Disease.builder()
                .name(request.getName())
                .description(request.getDescription())
                .speciality(speciality)
                .build();

        repository.save(disease);

    }

    @Override
    public void updateDisease(Long id ,DiseaseRequest request) {

        Disease disease = repository.findById(id).orElseThrow(
                () -> new BadRequestException("this speciality is not exist")
        );
        if (request.getName() !=null){
            disease.setName(request.getName());
        }
        if (request.getDescription() !=null){
            disease.setDescription(request.getDescription());
        }
        if (request.getSpecialityId() != null){
            Speciality speciality = specialityRepository.findById(request.getSpecialityId()).orElseThrow(
                    () -> new BadRequestException("this speciality is not exist")
            );
            disease.setSpeciality(speciality);
        }
        repository.save(disease);
    }

    @Override
    public void deleteDisease(Long id) {

        if (repository.existsById(id)){
            repository.deleteById(id);
        }
        throw new BadRequestException("this speciality is not exist");

    }
}
