package com.temantani.investment.service.domain;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.investment.service.domain.entity.Project;
import com.temantani.investment.service.domain.ports.input.message.FundraisingRegisteredMessageListener;
import com.temantani.investment.service.domain.ports.output.repository.ProjectRepository;

@Component
public class FundraisingRegisteredMessageListenerImpl implements FundraisingRegisteredMessageListener {

  private final ProjectRepository projectRepository;

  public FundraisingRegisteredMessageListenerImpl(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  @Transactional
  public void registerFundraisingProject(UUID projectId, Money fundraisingTarget, ZonedDateTime fundraisingDeadline) {
    Project newProject = Project.createProject(new ProjectId(projectId), fundraisingTarget, fundraisingDeadline);
    projectRepository.create(newProject);
  }

}
