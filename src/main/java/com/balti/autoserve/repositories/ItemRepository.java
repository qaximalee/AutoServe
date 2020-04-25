package com.balti.autoserve.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.balti.autoserve.entities.Item;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long>{

}
