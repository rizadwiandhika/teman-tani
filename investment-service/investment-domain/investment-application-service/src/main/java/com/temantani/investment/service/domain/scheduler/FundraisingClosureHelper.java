package com.temantani.investment.service.domain.scheduler;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.investment.service.domain.InvestmentDomainService;
import com.temantani.investment.service.domain.entity.Project;
import com.temantani.investment.service.domain.event.ProjectClosureEvent;
import com.temantani.investment.service.domain.mapper.InvestmentDataMapper;
import com.temantani.investment.service.domain.outbox.scheduler.fundraisingclosure.FundraisingOutboxHelper;
import com.temantani.investment.service.domain.ports.output.repository.ProjectRepository;
import com.temantani.investment.service.domain.valueobject.ProjectStatus;

@Component
public class FundraisingClosureHelper {

  private final InvestmentDomainService domainService;
  private final ProjectRepository projectRepository;
  private final FundraisingOutboxHelper fundraisingOutboxHelper;
  private final InvestmentDataMapper mapper;

  public FundraisingClosureHelper(InvestmentDomainService domainService, ProjectRepository projectRepository,
      FundraisingOutboxHelper fundraisingOutboxHelper, InvestmentDataMapper mapper) {
    this.domainService = domainService;
    this.projectRepository = projectRepository;
    this.fundraisingOutboxHelper = fundraisingOutboxHelper;
    this.mapper = mapper;
  }

  @Transactional
  public void closeForAllClosingFundraising() {
    Optional<List<Project>> closingProjectsOp = projectRepository.findByStatus(ProjectStatus.CLOSING);
    if (closingProjectsOp.isEmpty()) {
      return;
    }

    closingProjectsOp.get().stream().forEach(project -> {
      ProjectClosureEvent event = domainService.closeProjectForInvestment(project);

      projectRepository.save(event.getProject());
      fundraisingOutboxHelper.createOutbox(mapper.projectClosureEventToFundraisingClosureEventPayload(event));
    });
  }

}
