package com.balti.autoserve.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.balti.autoserve.entities.Orders;

public interface OrdersRepository extends PagingAndSortingRepository<Orders, Long>{

}
