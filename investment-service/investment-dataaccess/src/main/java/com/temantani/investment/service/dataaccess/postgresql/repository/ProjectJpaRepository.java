package com.temantani.investment.service.dataaccess.postgresql.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.temantani.investment.service.dataaccess.postgresql.entity.ProjectEntity;
import com.temantani.investment.service.domain.valueobject.ProjectStatus;

@Repository
public interface ProjectJpaRepository extends JpaRepository<ProjectEntity, UUID> {

  Optional<List<ProjectEntity>> findByStatus(ProjectStatus status);

  @Query("SELECT p FROM ProjectEntity p WHERE p.id = (SELECT i.project.id FROM InvestmentEntity i WHERE i.id = :investmentId)")
  Optional<ProjectEntity> findByInvestmentId(@Param("investmentId") UUID investmentId);

}
