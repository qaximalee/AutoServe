package com.balti.autoserve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.balti.autoserve.entities.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>{

}
