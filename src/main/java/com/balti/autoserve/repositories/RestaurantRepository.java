package com.balti.autoserve.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.balti.autoserve.entities.Restaurant;

public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant, Long>, JpaRepository<Restaurant, Long> {

	Optional<Restaurant> findByEmail(String email);
	
	Optional<Restaurant> findByWebsite(String website);
	
	Optional<Restaurant> findByName(String name);
	
	List<Restaurant> findByNameLike(String name);
	
	List<Restaurant> findByCityAndActiveTrue(String city);
	
	List<Restaurant> findByProvinceAndActiveTrue(String province);
	
	List<Restaurant> findByCountryAndActiveTrue(String country);
	
	Optional<Restaurant> findByPhone1(String phone1);
	
	Optional<Restaurant> findByPhone2(String phone2);
	
	Page<Restaurant> findByActiveTrue(Pageable pageable);
}
