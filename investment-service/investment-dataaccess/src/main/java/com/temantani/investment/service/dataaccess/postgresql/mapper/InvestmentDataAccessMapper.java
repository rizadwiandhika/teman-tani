package com.temantani.investment.service.dataaccess.postgresql.mapper;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.investment.service.dataaccess.postgresql.entity.InvestmentEntity;
import com.temantani.investment.service.dataaccess.postgresql.entity.InvestorEntity;
import com.temantani.investment.service.dataaccess.postgresql.entity.ProjectEntity;
import com.temantani.investment.service.domain.entity.Investment;
import com.temantani.investment.service.domain.entity.Investor;
import com.temantani.investment.service.domain.entity.Project;

@Component
public class InvestmentDataAccessMapper {

  // Investment to InvestmentEntity method
  public InvestmentEntity investmentToInvestmentEntity(Investment investment) {
    return InvestmentEntity.builder()
        .id(investment.getId().getValue())
        .version(investment.getVersion())
        .projectId(investment.getProjectId().getValue())
        .investorId(investment.getInvestorId().getValue())
        .amount(investment.getAmount().getAmount())
        .status(investment.getStatus())
        .failureReasons(investment.getFailureReasons() == null ? null
            : String.join(Investment.FAILURE_REASONS_DELIMITER, investment.getFailureReasons()))
        .build();
  }

  public Investment investmentEntityToInvestment(InvestmentEntity investmentEntity) {
    return Investment.builder()
        .id(new InvestmentId(investmentEntity.getId()))
        .version(investmentEntity.getVersion())
        .projectId(new ProjectId(investmentEntity.getProjectId()))
        .investorId(new UserId(investmentEntity.getInvestorId()))
        .amount(new Money(investmentEntity.getAmount()))
        .status(investmentEntity.getStatus())
        .failureReasons(investmentEntity.getFailureReasons() == null
            ? null
            : Arrays.asList(investmentEntity.getFailureReasons().split(Investment.FAILURE_REASONS_DELIMITER)))
        .build();
  }

  public InvestorEntity investorToInvestorEntity(Investor investor) {
    return InvestorEntity.builder()
        .id(investor.getId().getValue())
        .name(investor.getName())
        .email(investor.getEmail())
        .profilePictureUrl(investor.getProfilePictureUrl())
        .build();
  }

  public Investor investorEntityToInvestor(InvestorEntity entity) {
    return Investor.builder()
        .id(new UserId(entity.getId()))
        .name(entity.getName())
        .email(entity.getEmail())
        .profilePictureUrl(entity.getProfilePictureUrl())
        .build();
  }

  public ProjectEntity projectToProjectEntity(Project project) {
    return ProjectEntity.builder()
        .id(project.getId().getValue())
        .status(project.getStatus())
        .fundraisingTarget(project.getFundraisingTarget().getAmount())
        .collectedFunds(project.getCollectedFunds().getAmount())
        .description(project.getDescription())
        .tenorDeadline(project.getTenorDeadline())
        .createdAt(project.getCreatedAt())
        .version(project.getVersion())
        .build();
  }

  public Project projectEntityToProject(ProjectEntity entity) {
    return Project.builder()
        .id(new ProjectId(entity.getId()))
        .status(entity.getStatus())
        .fundraisingTarget(new Money(entity.getFundraisingTarget()))
        .collectedFunds(new Money(entity.getCollectedFunds()))
        .description(entity.getDescription())
        .tenorDeadline(entity.getTenorDeadline())
        .createdAt(entity.getCreatedAt())
        .version(entity.getVersion())
        .build();
  }

}
