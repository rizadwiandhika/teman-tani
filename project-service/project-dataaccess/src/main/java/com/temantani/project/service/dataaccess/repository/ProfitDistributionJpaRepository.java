package com.temantani.project.service.dataaccess.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.temantani.project.service.dataaccess.entity.ProfitDistributionEntity;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfitDistributionJpaRepository extends JpaRepository<ProfitDistributionEntity, UUID> {

  Optional<List<ProfitDistributionEntity>> findByProjectId(UUID projectId);

}
