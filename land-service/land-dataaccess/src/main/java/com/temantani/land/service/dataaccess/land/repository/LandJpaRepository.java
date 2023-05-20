package com.temantani.land.service.dataaccess.land.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.temantani.land.service.dataaccess.land.entity.LandEntity;

@Repository
public interface LandJpaRepository extends JpaRepository<LandEntity, UUID> {

}
