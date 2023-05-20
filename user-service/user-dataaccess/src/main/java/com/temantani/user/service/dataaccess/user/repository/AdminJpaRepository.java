package com.temantani.user.service.dataaccess.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.temantani.user.service.dataaccess.user.entity.AdminEntity;

@Repository
public interface AdminJpaRepository extends JpaRepository<AdminEntity, UUID> {

  Optional<AdminEntity> findByEmail(String email);

}
