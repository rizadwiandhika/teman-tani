package com.temantani.project.service.domain;

import static com.temantani.domain.helper.Helper.now;

import java.util.List;
import java.util.Map;

import com.temantani.domain.valueobject.BankAccount;
import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.domain.entity.Expense;
import com.temantani.project.service.domain.entity.Investment;
import com.temantani.project.service.domain.entity.Land;
import com.temantani.project.service.domain.entity.ProfitDistribution;
import com.temantani.project.service.domain.entity.Project;
import com.temantani.project.service.domain.entity.Sale;
import com.temantani.project.service.domain.event.ProjectCreatedEvent;
import com.temantani.project.service.domain.event.ProjectStatusUpdatedEvent;
import com.temantani.project.service.domain.exception.ProjectDomainException;
import com.temantani.project.service.domain.valueobject.LandAvailabilityStatus;
import com.temantani.project.service.domain.valueobject.ProfitDistributionDetailId;

public class DomainServiceImpl implements DomainService {

  @Override
  public ProjectStatusUpdatedEvent cancelProject(UserId maangerId, Project project, Land land, List<String> reasons) {
    project.cancel(maangerId, reasons);
    land.releaseLand();
    return new ProjectStatusUpdatedEvent(project, now());
  }

  @Override
  public ProjectStatusUpdatedEvent continueProjectToHiring(UserId managerId, Project project) {
    project.continueToHiring(managerId);
    return new ProjectStatusUpdatedEvent(project, now());
  }

  @Override
  public ProjectStatusUpdatedEvent executeProject(UserId managerId, Project project) {
    project.execute(managerId);
    return new ProjectStatusUpdatedEvent(project, now());
  }

  @Override
  public ProjectStatusUpdatedEvent finishProject(UserId managerId, Project project, Land land) {
    project.finish(managerId);
    land.releaseLand();

    return new ProjectStatusUpdatedEvent(project, now());
  }

  @Override
  public ProjectCreatedEvent validateAndInitiateProject(UserId managerId, Project project, Land land) {
    if (land.getAvailabilityStatus() == LandAvailabilityStatus.RESERVED) {
      throw new ProjectDomainException("Land is already reserved");
    }

    project.validateProjectInitialization();
    project.createProject(land.getId(), managerId, land.getOwnerId());

    land.reserveLand();

    return new ProjectCreatedEvent(project, now());
  }

  @Override
  public void addProjectExpense(UserId managerId, Project project, Expense expense) {
    project.addExpense(managerId, expense);
  }

  @Override
  public Investment addInvestment(Project project, InvestmentId investmentId, UserId investorId, Money amount) {
    return project.addInvestment(investmentId, investorId, amount);
  }

  @Override
  public void addHarvestSelling(Project project, Sale sale) {
    project.addIncome(sale.getAmount());
  }

  @Override
  public ProfitDistribution initiateProfitDistribution(UserId managerId, Project project,
      Map<UserId, BankAccount> receiverBank) {
    return project.initiateProfitDistribution(managerId, receiverBank);
  }

  @Override
  public void completeProfitDistribution(UserId managetId, ProfitDistribution profitDistribution,
      Map<ProfitDistributionDetailId, String> transferProofs) {
    profitDistribution.transferProfit(managetId, transferProofs);
  }

}
