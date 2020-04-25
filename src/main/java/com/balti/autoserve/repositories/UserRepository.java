package com.balti.autoserve.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.balti.autoserve.entities.Address;
import com.balti.autoserve.entities.Restaurant;
import com.balti.autoserve.entities.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaRepository<User, Long>{
	
	Page<User> findByRoles(String roles, Pageable page);
	
	Optional<User> findUserByUserName(String userName);
	
	Optional<User> findUserByEmail(String email);
	
	List<User> findAllByAddress(Address address);
	
	List<User> findUserByRolesAndRestaurant(String roles, Restaurant restaurant);
	
	boolean existsByUserName(String userName);
	
	boolean existsByEmail(String email);
}
