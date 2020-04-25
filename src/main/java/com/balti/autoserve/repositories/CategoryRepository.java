package com.balti.autoserve.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.balti.autoserve.entities.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long>{

}
