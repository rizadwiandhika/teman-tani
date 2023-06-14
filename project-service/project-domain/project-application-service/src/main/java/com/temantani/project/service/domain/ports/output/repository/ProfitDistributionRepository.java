package com.temantani.project.service.domain.ports.output.repository;

import java.util.List;
import java.util.Optional;

import com.temantani.domain.valueobject.ProjectId;
import com.temantani.project.service.domain.entity.ProfitDistribution;
import com.temantani.project.service.domain.valueobject.ProfitDistributionId;

public interface ProfitDistributionRepository {

  Optional<ProfitDistribution> findById(ProfitDistributionId profitDistributionId);

  Optional<List<ProfitDistribution>> findByProjectId(ProjectId projectId);

  ProfitDistribution save(ProfitDistribution profitDistribution);

}
