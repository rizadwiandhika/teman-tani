package com.temantani.investment.service.domain;

import static com.temantani.domain.DomainConstant.TIMEZONE;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import com.temantani.investment.service.domain.entity.Investment;
import com.temantani.investment.service.domain.entity.Project;
import com.temantani.investment.service.domain.event.InvestmentPaidEvent;

public class InvestmentDomainServiceImpl implements InvestmentDomainService {

  @Override
  public void cancelInvestment(Investment investment, List<String> reasons) {
    investment.cancel(reasons);
  }

  @Override
  public InvestmentPaidEvent payInvestment(Investment investment, Project project) {
    investment.pay();
    project.acceptInvestment(investment.getAmount());

    return new InvestmentPaidEvent(investment, ZonedDateTime.now(ZoneId.of(TIMEZONE)));
  }

  @Override
  public void validateAndInitiateInvestment(Investment investment, Project project) {
    project.createInvestment(investment);
  }

}
