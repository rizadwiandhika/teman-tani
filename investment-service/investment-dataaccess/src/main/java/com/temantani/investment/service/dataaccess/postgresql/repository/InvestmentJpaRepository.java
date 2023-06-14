package com.temantani.investment.service.dataaccess.postgresql.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.temantani.investment.service.dataaccess.postgresql.entity.InvestmentEntity;
import com.temantani.investment.service.domain.valueobject.InvestmentStatus;

@Repository
public interface InvestmentJpaRepository extends JpaRepository<InvestmentEntity, UUID> {

  Optional<List<InvestmentEntity>> findByStatus(InvestmentStatus status);

  Optional<List<InvestmentEntity>> findByInvestorId(UUID investorId);

}
