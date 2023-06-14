package com.temantani.project.service.domain.outbox.scheduler.fundraisingregistered;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temantani.domain.helper.Helper;
import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.project.service.domain.exception.ProjectDomainException;
import com.temantani.project.service.domain.outbox.model.fundraisingregistered.FundraisingRegisteredEventPayload;
import com.temantani.project.service.domain.outbox.model.fundraisingregistered.FundraisingRegisteredOutboxMessage;
import com.temantani.project.service.domain.ports.output.repository.FundraisingRegisteredOutboxRepository;

@Component
public class FundraisingRegisteredOutboxHelper {

  private final FundraisingRegisteredOutboxRepository projectOutboxRepository;
  private final ObjectMapper objectMapper;

  public FundraisingRegisteredOutboxHelper(FundraisingRegisteredOutboxRepository projectOutboxRepository,
      ObjectMapper objectMapper) {
    this.projectOutboxRepository = projectOutboxRepository;
    this.objectMapper = objectMapper;
  }

  @Transactional(readOnly = true)
  public List<FundraisingRegisteredOutboxMessage> getOutbox(OutboxStatus... status) {
    return projectOutboxRepository.findByOutboxStatuses(status).orElse(new ArrayList<>());
  }

  @Transactional
  public void updateOutboxStatus(FundraisingRegisteredOutboxMessage outboxMessage, OutboxStatus outboxStatus) {
    outboxMessage.setOutboxStatus(outboxStatus);
    outboxMessage.setProcessedAt(Helper.now());
    save(outboxMessage);
  }

  @Transactional
  public void createOutbox(FundraisingRegisteredEventPayload payload) {
    FundraisingRegisteredOutboxMessage outboxMessage = FundraisingRegisteredOutboxMessage.builder()
        .id(UUID.randomUUID())
        .payload(writePayload(payload))
        .outboxStatus(OutboxStatus.STARTED)
        .createdAt(Helper.now())
        .build();

    save(outboxMessage);
  }

  private void save(FundraisingRegisteredOutboxMessage outboxMessage) {
    try {
      outboxMessage = projectOutboxRepository.save(outboxMessage);
      if (outboxMessage == null) {
        throw new ProjectDomainException("Unable to save ProjectStatusUpdatedOutboxMessage");
      }
    } catch (Exception e) {
      throw e;
    }
  }

  private String writePayload(FundraisingRegisteredEventPayload payload) {
    try {
      return objectMapper.writeValueAsString(payload);
    } catch (Exception e) {
      throw new ProjectDomainException("Unable to write ProjectStatusUpdatedEventPayload as JSON");
    }
  }

}
