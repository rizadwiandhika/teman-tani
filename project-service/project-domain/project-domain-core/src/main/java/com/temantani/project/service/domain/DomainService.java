package com.temantani.project.service.domain;

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
import com.temantani.project.service.domain.valueobject.ProfitDistributionDetailId;

public interface DomainService {

    ProjectCreatedEvent validateAndInitiateProject(UserId managerId, Project project, Land land);

    ProjectStatusUpdatedEvent continueProjectToHiring(UserId managerId, Project project);

    ProjectStatusUpdatedEvent executeProject(UserId managerId, Project project);

    ProjectStatusUpdatedEvent finishProject(UserId managerId, Project project, Land land);

    ProjectStatusUpdatedEvent cancelProject(UserId maangerId, Project project, Land land, List<String> reasons);

    void addProjectExpense(UserId managerId, Project project, Expense expense);

    // For messaging
    Investment addInvestment(Project project, InvestmentId investmentId, UserId investorId, Money amount);

    // For messaging
    void addHarvestSelling(Project project, Sale sale);

    ProfitDistribution initiateProfitDistribution(UserId managerId, Project project,
            Map<UserId, BankAccount> receiverBank);

    public void completeProfitDistribution(UserId managerId, ProfitDistribution profitDistribution,
            Map<ProfitDistributionDetailId, String> transferProofs);

}
