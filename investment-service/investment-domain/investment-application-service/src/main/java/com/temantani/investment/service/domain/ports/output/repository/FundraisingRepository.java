package com.temantani.investment.service.domain.ports.output.repository;

import java.util.List;
import java.util.Optional;

import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.investment.service.domain.entity.Fundraising;
import com.temantani.investment.service.domain.valueobject.FundraisingStatus;

public interface FundraisingRepository {

  Optional<List<Fundraising>> findByStatus(FundraisingStatus status);

  Optional<Fundraising> findById(ProjectId projectId);

  Optional<Fundraising> findByInvestmentId(InvestmentId investmentId);

  Fundraising save(Fundraising project);

  Fundraising create(Fundraising project);

  // List<Project> saveAll(List<Project> project);

}
