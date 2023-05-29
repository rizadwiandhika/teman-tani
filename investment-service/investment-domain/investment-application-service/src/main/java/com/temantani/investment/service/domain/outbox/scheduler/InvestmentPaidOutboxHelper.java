package com.temantani.investment.service.domain.outbox.scheduler;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.temantani.domain.helper.Helper;
import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.investment.service.domain.exception.InvestmentDomainException;
import com.temantani.investment.service.domain.outbox.model.InvestmentPaidEventPayload;
import com.temantani.investment.service.domain.outbox.model.InvestmentPaidOutboxMessage;
import com.temantani.investment.service.domain.ports.output.repository.InvestmentPaidOutboxRepository;

@Component
public class InvestmentPaidOutboxHelper {

  private final InvestmentPaidOutboxRepository repo;
  private final ObjectMapper objectMapper;

  public InvestmentPaidOutboxHelper(InvestmentPaidOutboxRepository repo, ObjectMapper objectMapper) {
    this.repo = repo;
    this.objectMapper = objectMapper;
  }

  @Transactional(readOnly = true)
  public List<InvestmentPaidOutboxMessage> getOutbox() {
    return repo.findByOutboxStatuses(OutboxStatus.STARTED, OutboxStatus.FAILED).orElse(List.of());
  }

  @Transactional
  public void createOutbox(InvestmentPaidEventPayload payload) {
    InvestmentPaidOutboxMessage outbox = InvestmentPaidOutboxMessage.builder()
        .id(UUID.randomUUID())
        .payload(writePayload(payload))
        .outboxStatus(OutboxStatus.STARTED)
        .createdAt(Helper.now())
        .build();

    save(outbox);
  }

  @Transactional
  public void updateOutbox(InvestmentPaidOutboxMessage outbox, OutboxStatus status) {
    outbox.setOutboxStatus(status);
    outbox.setProcessedAt(Helper.now());
    save(outbox);
  }

  private void save(InvestmentPaidOutboxMessage outbox) {
    repo.save(outbox);
  }

  private String writePayload(InvestmentPaidEventPayload payload) {
    try {
      return objectMapper.writeValueAsString(payload);
    } catch (JsonProcessingException e) {
      throw new InvestmentDomainException(e.getMessage(), e);
    }
  }

}
