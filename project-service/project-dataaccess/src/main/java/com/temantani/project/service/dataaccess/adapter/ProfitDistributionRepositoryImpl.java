package com.temantani.project.service.dataaccess.adapter;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.temantani.project.service.dataaccess.mapper.ProjectDataAccessMapper;
import com.temantani.project.service.dataaccess.repository.ProfitDistributionJpaRepository;
import com.temantani.project.service.domain.entity.ProfitDistribution;
import com.temantani.project.service.domain.ports.output.repository.ProfitDistributionRepository;
import com.temantani.project.service.domain.valueobject.ProfitDistributionId;

@Repository
public class ProfitDistributionRepositoryImpl implements ProfitDistributionRepository {

  private final ProfitDistributionJpaRepository repo;
  private final ProjectDataAccessMapper mapper;

  public ProfitDistributionRepositoryImpl(ProfitDistributionJpaRepository repo, ProjectDataAccessMapper mapper) {
    this.repo = repo;
    this.mapper = mapper;
  }

  @Override
  public Optional<ProfitDistribution> findById(ProfitDistributionId profitDistributionId) {
    return repo.findById(profitDistributionId.getValue()).map(mapper::profitDistributionEntityToProfitDistribution);
  }

  @Override
  public ProfitDistribution save(ProfitDistribution profitDistribution) {
    return mapper.profitDistributionEntityToProfitDistribution(
        repo.save(mapper.profitDistributioToProfitDistributionEntity(profitDistribution)));
  }

}
