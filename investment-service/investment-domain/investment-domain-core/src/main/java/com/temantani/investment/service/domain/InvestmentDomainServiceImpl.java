package com.temantani.investment.service.domain;

import static com.temantani.domain.DomainConstant.TIMEZONE;
import static com.temantani.investment.service.domain.valueobject.ProjectStatus.CLOSED;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import com.temantani.investment.service.domain.entity.Investment;
import com.temantani.investment.service.domain.entity.Project;
import com.temantani.investment.service.domain.event.InvestmentPaidEvent;
import com.temantani.investment.service.domain.exception.InvestmentDomainException;

public class InvestmentDomainServiceImpl implements InvestmentDomainService {

  @Override
  public void cancelInvestment(Investment investment, List<String> reasons) {
    investment.cancel(reasons);
  }

  @Override
  public InvestmentPaidEvent payInvestment(Investment investment, Project project) {
    project.addInvestment(investment.getAmount());
    investment.pay();
    return new InvestmentPaidEvent(investment, ZonedDateTime.now(ZoneId.of(TIMEZONE)));
  }

  @Override
  public void validateAndInitiateInvestment(Investment investment, Project project) {
    if (project.getStatus() == CLOSED) {
      throw new InvestmentDomainException("Project is already closed");
    }

    if (project.getCollectedFunds().isGreaterThan(project.getFundraisingTarget())) {
      throw new InvestmentDomainException("Project has already reached its fundraising target");
    }

    if (project.getCollectedFunds().add(investment.getAmount()).isGreaterThan(project.getFundraisingTarget())) {
      throw new InvestmentDomainException("Investment amount is too large, exceeding the fundraising target");
    }

    ZonedDateTime now = ZonedDateTime.now(ZoneId.of(TIMEZONE));
    if (project.getTenorDeadline().isBefore(now)) {
      throw new InvestmentDomainException("Project has already reached its fundraising end date");
    }

    investment.validateInvestment();
    investment.initializeInvestment();
  }

}
