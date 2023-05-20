package com.temantani.investment.service.dataaccess.postgresql.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.temantani.investment.service.dataaccess.postgresql.entity.InvestorEntity;

@Repository
public interface InvestorJpaRepository extends JpaRepository<InvestorEntity, UUID> {

}
