package com.temantani.investment.service.dataaccess.postgresql.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.temantani.investment.service.dataaccess.postgresql.entity.InvestmentEntity;

@Repository
public interface InvestmentJpaRepository extends JpaRepository<InvestmentEntity, UUID> {

}
