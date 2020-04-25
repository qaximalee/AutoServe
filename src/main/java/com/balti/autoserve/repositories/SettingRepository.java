package com.balti.autoserve.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.balti.autoserve.entities.Setting;

public interface SettingRepository extends JpaRepository<Setting, Long> {

}
