package com.balti.autoserve.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.balti.autoserve.entities.Deal;

public interface DealRepository extends PagingAndSortingRepository<Deal, Long>{

}
