package com.balti.autoserve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.balti.autoserve.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

}
