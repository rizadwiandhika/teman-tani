package com.temantani.investment.service.domain.outbox.scheduler.fundraisingclosure;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.temantani.domain.helper.Helper;
import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.investment.service.domain.exception.InvestmentDomainException;
import com.temantani.investment.service.domain.outbox.model.fundraisingclosure.FundraisingClosureEventPayload;
import com.temantani.investment.service.domain.outbox.model.fundraisingclosure.FundraisingClosureOutboxMessage;
import com.temantani.investment.service.domain.ports.output.repository.FundraisingClosureOutboxRepository;

@Component
public class FundraisingOutboxHelper {

  private final FundraisingClosureOutboxRepository outboxRepository;
  private final ObjectMapper objectMapper;

  public FundraisingOutboxHelper(FundraisingClosureOutboxRepository outboxRepository, ObjectMapper objectMapper) {
    this.outboxRepository = outboxRepository;
    this.objectMapper = objectMapper;
  }

  @Transactional(readOnly = true)
  public List<FundraisingClosureOutboxMessage> getOutboxes() {
    return outboxRepository.findByOutboxStatuses(OutboxStatus.STARTED, OutboxStatus.FAILED).orElse(new ArrayList<>());
  }

  @Transactional
  public void createOutbox(FundraisingClosureEventPayload payload) {
    FundraisingClosureOutboxMessage outbox = FundraisingClosureOutboxMessage.builder()
        .id(UUID.randomUUID())
        .payload(writePayload(payload))
        .createdAt(Helper.now())
        .outboxStatus(OutboxStatus.STARTED)
        .build();

    outboxRepository.save(outbox);
  }

  @Transactional
  public void updateOutboxStatus(FundraisingClosureOutboxMessage outbox, OutboxStatus status) {
    outbox.setOutboxStatus(status);
    outbox.setProcessedAt(Helper.now());

    outboxRepository.save(outbox);
  }

  private String writePayload(FundraisingClosureEventPayload payload) {
    try {
      return objectMapper.writeValueAsString(payload);
    } catch (JsonProcessingException e) {
      throw new InvestmentDomainException("Unable to write payload as JSON", e);
    }
  }

}
