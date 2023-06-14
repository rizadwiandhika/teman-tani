package com.temantani.project.service.dataaccess.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.ProjectId;
import com.temantani.project.service.dataaccess.mapper.ProjectDataAccessMapper;
import com.temantani.project.service.dataaccess.repository.ProfitDistributionJpaRepository;
import com.temantani.project.service.domain.entity.ProfitDistribution;
import com.temantani.project.service.domain.ports.output.repository.ProfitDistributionRepository;
import com.temantani.project.service.domain.valueobject.ProfitDistributionId;

@Component
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

  @Override
  public Optional<List<ProfitDistribution>> findByProjectId(ProjectId projectId) {
    return repo.findByProjectId(projectId.getValue())
        .map(l -> l.stream().map(mapper::profitDistributionEntityToProfitDistribution).collect(Collectors.toList()));
  }

}
