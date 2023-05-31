package com.temantani.investment.service.domain;

import java.util.List;

import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.Money;
import com.temantani.investment.service.domain.entity.Investor;
import com.temantani.investment.service.domain.entity.Project;
import com.temantani.investment.service.domain.event.InvestmentCreatedEvent;
import com.temantani.investment.service.domain.event.ProjectClosureEvent;

public class InvestmentDomainServiceImpl implements InvestmentDomainService {

  @Override
  public void cancelInvestment(Project project, InvestmentId investmentId, List<String> reasons) {
    project.cancelInvestment(investmentId, reasons);
  }

  @Override
  public ProjectClosureEvent closeProjectForInvestment(Project project) {
    project.cancelAllExipiredInvestments();
    return project.close();
  }

  @Override
  public void payInvestment(Project project, InvestmentId investmentId) {
    project.acceptInvestment(investmentId);
  }

  @Override
  public InvestmentCreatedEvent validateAndInitiateInvestment(Project project, Investor investor, Money amount) {
    return project.createInvestment(investor.getId(), amount);
  }

  // @Override
  // public void cancelInvestment(Investment investment, List<String> reasons) {
  // investment.cancel(reasons);
  // }

  // @Override
  // public InvestmentPaidEvent payInvestment(Investment investment, Project
  // project) {
  // investment.pay();
  // project.acceptInvestment(investment.getAmount());

  // return new InvestmentPaidEvent(investment,
  // ZonedDateTime.now(ZoneId.of(TIMEZONE)));
  // }

  // @Override
  // public void validateAndInitiateInvestment(Investment investment, Project
  // project) {
  // project.createInvestment(investment);
  // }

}
