package com.temantani.project.service.domain.outbox.scheduler.closefundraisingrequested;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.temantani.domain.helper.Helper;
import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.project.service.domain.exception.ProjectDomainException;
import com.temantani.project.service.domain.outbox.model.closefundraisingrequested.CloseFundraisingRqeustedEventPayload;
import com.temantani.project.service.domain.outbox.model.closefundraisingrequested.CloseFundraisingRequestedOutboxMessage;
import com.temantani.project.service.domain.ports.output.repository.CloseFundraisingRequestedOutboxRepository;

@Component
public class CloseFundraisingRequestedOutboxHelper {

  private final CloseFundraisingRequestedOutboxRepository outboxRepository;
  private final ObjectMapper objectmapper;

  public CloseFundraisingRequestedOutboxHelper(CloseFundraisingRequestedOutboxRepository outboxRepository,
      ObjectMapper objectmapper) {
    this.outboxRepository = outboxRepository;
    this.objectmapper = objectmapper;
  }

  @Transactional(readOnly = true)
  public List<CloseFundraisingRequestedOutboxMessage> getOutbox(OutboxStatus... status) {
    return outboxRepository.findByOutboxStatuses(status).orElse(new ArrayList<>());
  }

  @Transactional
  public void createOutbox(CloseFundraisingRqeustedEventPayload payload) {
    CloseFundraisingRequestedOutboxMessage outbox = CloseFundraisingRequestedOutboxMessage.builder()
        .id(UUID.randomUUID())
        .outboxStatus(OutboxStatus.STARTED)
        .createdAt(Helper.now())
        .payload(writeJsonPayload(payload))
        .build();

    outboxRepository.save(outbox);
  }

  @Transactional
  public void updateOutboxStatus(CloseFundraisingRequestedOutboxMessage outbox, OutboxStatus status) {
    outbox.setProcessedAt(Helper.now());
    outbox.setOutboxStatus(status);

    outboxRepository.save(outbox);
  }

  private String writeJsonPayload(CloseFundraisingRqeustedEventPayload payload) {
    try {
      return objectmapper.writeValueAsString(payload);
    } catch (JsonProcessingException e) {
      throw new ProjectDomainException("Unable to write payload", e);
    }
  }

}
