package com.temantani.investment.service.domain;

import java.util.List;

import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.Money;
import com.temantani.investment.service.domain.entity.Investor;
import com.temantani.investment.service.domain.entity.Fundraising;
import com.temantani.investment.service.domain.event.InvestmentCreatedEvent;
import com.temantani.investment.service.domain.event.ProjectClosureEvent;

public interface InvestmentDomainService {
  InvestmentCreatedEvent validateAndInitiateInvestment(Fundraising project, Investor investor, Money amount);

  void payInvestment(Fundraising project, InvestmentId investmentId);

  void cancelInvestment(Fundraising project, InvestmentId investmentId, List<String> reasons);

  ProjectClosureEvent closeProjectForInvestment(Fundraising project);
}
