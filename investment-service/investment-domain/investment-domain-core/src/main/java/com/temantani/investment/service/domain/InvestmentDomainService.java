package com.temantani.investment.service.domain;

import java.util.List;

import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.Money;
import com.temantani.investment.service.domain.entity.Investor;
import com.temantani.investment.service.domain.entity.Project;
import com.temantani.investment.service.domain.event.InvestmentCreatedEvent;
import com.temantani.investment.service.domain.event.ProjectClosureEvent;

public interface InvestmentDomainService {
  InvestmentCreatedEvent validateAndInitiateInvestment(Project project, Investor investor, Money amount);

  void payInvestment(Project project, InvestmentId investmentId);

  void cancelInvestment(Project project, InvestmentId investmentId, List<String> reasons);

  ProjectClosureEvent closeProjectForInvestment(Project project);
}
