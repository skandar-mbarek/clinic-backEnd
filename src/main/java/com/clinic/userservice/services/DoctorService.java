package com.clinic.userservice.services;

import com.clinic.userservice.dtos.DoctorDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DoctorService {
    public Page<DoctorDto> searchDoctors (Long specialityId, String city, int page, int size);
    public DoctorDto getById (Long id);

}
