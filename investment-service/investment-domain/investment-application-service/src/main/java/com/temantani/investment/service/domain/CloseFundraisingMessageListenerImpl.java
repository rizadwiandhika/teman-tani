package com.temantani.investment.service.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.domain.valueobject.ProjectId;
import com.temantani.investment.service.domain.entity.Fundraising;
import com.temantani.investment.service.domain.event.ProjectClosureEvent;
import com.temantani.investment.service.domain.exception.InvestmentDomainException;
import com.temantani.investment.service.domain.mapper.InvestmentDataMapper;
import com.temantani.investment.service.domain.outbox.scheduler.fundraisingclosure.FundraisingOutboxHelper;
import com.temantani.investment.service.domain.ports.input.message.CloseFundraisingMessageListener;
import com.temantani.investment.service.domain.ports.output.repository.FundraisingRepository;

@Component
public class CloseFundraisingMessageListenerImpl implements CloseFundraisingMessageListener {

  private final FundraisingRepository projectRepository;
  private final InvestmentDataMapper mapper;
  private final FundraisingOutboxHelper fundraisingOutboxHelper;

  public CloseFundraisingMessageListenerImpl(FundraisingRepository projectRepository, InvestmentDataMapper mapper,
      FundraisingOutboxHelper fundraisingOutboxHelper) {
    this.projectRepository = projectRepository;
    this.mapper = mapper;
    this.fundraisingOutboxHelper = fundraisingOutboxHelper;
  }

  @Override
  @Transactional
  public void closeFundraising(ProjectId projectId) {
    Fundraising project = projectRepository.findById(projectId)
        .orElseThrow(() -> new InvestmentDomainException("Project: " + projectId.getValue() + " not found"));

    ProjectClosureEvent event = project.close();

    projectRepository.save(project);
    fundraisingOutboxHelper.createOutbox(mapper.projectClosureEventToFundraisingClosureEventPayload(event));
  }

}
