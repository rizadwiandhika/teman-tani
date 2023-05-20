package com.temantani.land.service.dataaccess.borrower.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.temantani.land.service.dataaccess.borrower.entity.BorrowerEntity;

@Repository
public interface BorrowerJpaRepository extends JpaRepository<BorrowerEntity, UUID> {

}
