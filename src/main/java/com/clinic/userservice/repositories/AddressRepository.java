package com.clinic.userservice.repositories;

import com.clinic.userservice.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
