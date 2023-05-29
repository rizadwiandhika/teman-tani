package com.temantani.project.service.dataaccess.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.temantani.project.service.dataaccess.entity.ManagerEntity;

@Repository
public interface ManagerJpaRepository extends JpaRepository<ManagerEntity, UUID> {

}
