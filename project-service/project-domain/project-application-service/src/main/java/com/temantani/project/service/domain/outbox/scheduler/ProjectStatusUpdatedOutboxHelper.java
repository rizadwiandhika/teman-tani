package com.temantani.project.service.domain.outbox.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temantani.domain.helper.Helper;
import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.project.service.domain.exception.ProjectDomainException;
import com.temantani.project.service.domain.outbox.model.ProjectStatusUpdatedEventPayload;
import com.temantani.project.service.domain.outbox.model.ProjectStatusUpdatedOutboxMessage;
import com.temantani.project.service.domain.ports.output.repository.ProjectOutboxRepository;

@Component
public class ProjectStatusUpdatedOutboxHelper {

  private final ProjectOutboxRepository projectOutboxRepository;
  private final ObjectMapper objectMapper;

  public ProjectStatusUpdatedOutboxHelper(ProjectOutboxRepository projectOutboxRepository,
      ObjectMapper objectMapper) {
    this.projectOutboxRepository = projectOutboxRepository;
    this.objectMapper = objectMapper;
  }

  @Transactional(readOnly = true)
  public List<ProjectStatusUpdatedOutboxMessage> getProjectStatusUpdatedOutbox(OutboxStatus... status) {
    return projectOutboxRepository.findByOutboxStatuses(status).orElse(new ArrayList<>());
  }

  @Transactional
  public void updateOutboxStatus(ProjectStatusUpdatedOutboxMessage outboxMessage, OutboxStatus outboxStatus) {
    outboxMessage.setOutboxStatus(outboxStatus);
    outboxMessage.setProcessedAt(Helper.now());
    save(outboxMessage);
  }

  @Transactional
  public void createProjectStatusUpdatedOutbox(ProjectStatusUpdatedEventPayload payload) {
    ProjectStatusUpdatedOutboxMessage outboxMessage = ProjectStatusUpdatedOutboxMessage.builder()
        .id(UUID.randomUUID())
        .payload(writePayload(payload))
        .outboxStatus(OutboxStatus.STARTED)
        .createdAt(Helper.now())
        .build();

    save(outboxMessage);
  }

  private void save(ProjectStatusUpdatedOutboxMessage outboxMessage) {
    try {
      outboxMessage = projectOutboxRepository.save(outboxMessage);
      if (outboxMessage == null) {
        throw new ProjectDomainException("Unable to save ProjectStatusUpdatedOutboxMessage");
      }
    } catch (Exception e) {
      throw e;
    }
  }

  private String writePayload(ProjectStatusUpdatedEventPayload payload) {
    try {
      return objectMapper.writeValueAsString(payload);
    } catch (Exception e) {
      throw new ProjectDomainException("Unable to write ProjectStatusUpdatedEventPayload as JSON");
    }
  }

}
