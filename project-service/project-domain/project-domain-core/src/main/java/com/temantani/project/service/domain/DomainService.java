package com.temantani.project.service.domain;

import java.util.List;
import java.util.Map;

import com.temantani.domain.valueobject.BankAccount;
import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.domain.entity.Expense;
import com.temantani.project.service.domain.entity.Investment;
import com.temantani.project.service.domain.entity.Land;
import com.temantani.project.service.domain.entity.ProfitDistribution;
import com.temantani.project.service.domain.entity.Project;
import com.temantani.project.service.domain.entity.Sale;
import com.temantani.project.service.domain.event.ProjectCreatedEvent;
import com.temantani.project.service.domain.event.ProjectHiringInitializedEvent;
import com.temantani.project.service.domain.valueobject.ProfitDistributionDetailId;

public interface DomainService {

    ProjectCreatedEvent validateAndInitiateProject(UserId managerId, Project project, Land land);

    ProjectHiringInitializedEvent intiateToHiring(UserId managerId, Project project);

    // for messaging
    void proceededToHiring(Project project, List<Investment> investments);

    void executeProject(UserId managerId, Project project);

    void finishProject(UserId managerId, Project project, Land land);

    void cancelProject(UserId maangerId, Project project, Land land, List<String> reasons);

    void addProjectExpense(UserId managerId, Project project, Expense expense);

    // For messaging
    void addHarvestSelling(Project project, Sale sale);

    ProfitDistribution generateProfitDistribution(UserId managerId, Project project,
            Map<UserId, BankAccount> receiverBank);

    public void completeProfitDistribution(UserId managerId, ProfitDistribution profitDistribution,
            Map<ProfitDistributionDetailId, String> transferProofs);

}
