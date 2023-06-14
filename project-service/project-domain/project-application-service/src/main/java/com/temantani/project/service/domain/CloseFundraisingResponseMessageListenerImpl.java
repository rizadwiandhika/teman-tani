package com.temantani.project.service.domain;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.domain.valueobject.ProjectId;
import com.temantani.project.service.domain.entity.Investment;
import com.temantani.project.service.domain.entity.Project;
import com.temantani.project.service.domain.exception.ProjectDomainException;
import com.temantani.project.service.domain.ports.input.message.listener.CloseFundraisingResponseMessageListener;
import com.temantani.project.service.domain.ports.output.repository.InvestmentRepository;
import com.temantani.project.service.domain.ports.output.repository.ProjectRepository;

@Component
public class CloseFundraisingResponseMessageListenerImpl implements CloseFundraisingResponseMessageListener {

  private final DomainService domainService;
  private final ProjectRepository projectRepository;

  public CloseFundraisingResponseMessageListenerImpl(DomainService domainService, ProjectRepository projectRepository,
      InvestmentRepository investmentRepository) {
    this.domainService = domainService;
    this.projectRepository = projectRepository;
  }

  @Override
  @Transactional
  public void proceededToHiring(ProjectId projectId, List<Investment> investments) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new ProjectDomainException("Project not found: " + projectId.getValue()));

    domainService.proceededToHiring(project, investments);

    projectRepository.save(project);
    // investmentRepository.saveAll(investments);
  }

}
