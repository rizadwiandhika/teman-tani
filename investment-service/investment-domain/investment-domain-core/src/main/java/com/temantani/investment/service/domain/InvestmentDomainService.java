package com.temantani.investment.service.domain;

import java.util.List;

import com.temantani.investment.service.domain.entity.Investment;
import com.temantani.investment.service.domain.entity.Project;
import com.temantani.investment.service.domain.event.InvestmentPaidEvent;

public interface InvestmentDomainService {
  void validateAndInitiateInvestment(Investment investment, Project project);

  InvestmentPaidEvent payInvestment(Investment investment, Project project);

  void cancelInvestment(Investment investment, List<String> reasons);
}
