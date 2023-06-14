package com.temantani.investment.service.dataaccess.postgresql.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.temantani.investment.service.dataaccess.postgresql.entity.FundraisingEntity;
import com.temantani.investment.service.domain.valueobject.FundraisingStatus;

@Repository
public interface ProjectJpaRepository extends JpaRepository<FundraisingEntity, UUID> {

  Optional<List<FundraisingEntity>> findByStatus(FundraisingStatus status);

  @Query("SELECT p FROM ProjectEntity p WHERE p.id = (SELECT i.fundraising.id FROM InvestmentEntity i WHERE i.id = :investmentId)")
  Optional<FundraisingEntity> findByInvestmentId(@Param("investmentId") UUID investmentId);

}
