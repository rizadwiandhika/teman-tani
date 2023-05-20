package com.temantani.land.service.dataaccess.land.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.temantani.land.service.dataaccess.land.entity.ApproverEntity;

public interface ApproverJpaRepository extends JpaRepository<ApproverEntity, UUID> {

}
