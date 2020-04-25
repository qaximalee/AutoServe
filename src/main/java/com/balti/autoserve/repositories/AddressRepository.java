package com.balti.autoserve.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.balti.autoserve.entities.Address;
import com.balti.autoserve.entities.User;

public interface AddressRepository extends JpaRepository<Address, Long>{

	Optional<Address> findByUserId(Long userId);
}
