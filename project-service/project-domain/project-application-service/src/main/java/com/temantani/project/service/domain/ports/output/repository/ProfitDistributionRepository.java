package com.temantani.project.service.domain.ports.output.repository;

import java.util.Optional;

import com.temantani.project.service.domain.entity.ProfitDistribution;
import com.temantani.project.service.domain.valueobject.ProfitDistributionId;

public interface ProfitDistributionRepository {

  Optional<ProfitDistribution> findById(ProfitDistributionId profitDistributionId);

  ProfitDistribution save(ProfitDistribution profitDistribution);

}
