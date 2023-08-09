package com.temantani.project.service.domain;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.domain.exception.DataNotFoundException;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.project.service.domain.entity.Project;
import com.temantani.project.service.domain.ports.input.message.listener.OrderCreatedMessageListener;
import com.temantani.project.service.domain.ports.output.repository.ProjectRepository;

@Component
public class OrderCreatedMessageListenerImpl implements OrderCreatedMessageListener {

  private final ProjectRepository projectRepository;

  public OrderCreatedMessageListenerImpl(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Transactional
  @Override
  public void addIncomeFromOrder(UUID projectId, UUID orderId, Money amount) {
    Project project = projectRepository.findById(new ProjectId(projectId))
        .orElseThrow(() -> new DataNotFoundException("Project not found for :" + projectId.toString()));

    project.addIncomeFromOrder(orderId, amount);
    projectRepository.save(project);
  }

}
