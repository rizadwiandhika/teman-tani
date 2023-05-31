package com.temantani.project.service.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.domain.exception.DataAlreadyExistsException;
import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.domain.dto.message.investment.InvestmentPaidMessage;
import com.temantani.project.service.domain.entity.Investment;
import com.temantani.project.service.domain.entity.Project;
import com.temantani.project.service.domain.exception.ProjectDomainException;
import com.temantani.project.service.domain.ports.input.message.listener.InvestmentPaidMessageListener;
import com.temantani.project.service.domain.ports.output.repository.InvestmentRepository;
import com.temantani.project.service.domain.ports.output.repository.ProjectRepository;

@Component
public class InvestmentPaidMessageListenerImpl implements InvestmentPaidMessageListener {

  private final DomainService domainService;
  private final ProjectRepository projectRepository;
  private final InvestmentRepository investmentRepository;

  public InvestmentPaidMessageListenerImpl(DomainService domainService, ProjectRepository projectRepository,
      InvestmentRepository investmentRepository) {
    this.domainService = domainService;
    this.projectRepository = projectRepository;
    this.investmentRepository = investmentRepository;
  }

  @Override
  @Transactional
  public void addInvestmentToProject(InvestmentPaidMessage message)
      throws DataAlreadyExistsException, ProjectDomainException {
    Project project = findProjectByIdOrThrow(new ProjectId(message.getProjectId()));

    Investment investment = domainService.addInvestment(
        project,
        new InvestmentId(message.getInvestemntId()),
        new UserId(message.getInvestorId()),
        new Money(message.getAmount()));

    projectRepository.save(project);
    investment = investmentRepository.create(investment);
    if (investment == null) {
      throw new ProjectDomainException("Error saving investment: " + message.getInvestemntId());
    }
  }

  private Project findProjectByIdOrThrow(ProjectId projectId) {
    return projectRepository.findById(projectId)
        .orElseThrow(() -> new ProjectDomainException("Project not found: " + projectId.getValue()));
  }

}
