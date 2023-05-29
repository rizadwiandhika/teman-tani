package com.temantani.land.service.domain.outbox.scheduler;

import static com.temantani.domain.DomainConstant.TIMEZONE;
import static com.temantani.domain.outbox.OutboxStatus.STARTED;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.land.service.domain.exception.LandDomainException;
import com.temantani.land.service.domain.outbox.model.LandRegisteredEventPayload;
import com.temantani.land.service.domain.outbox.model.LandRegistetedOutboxMessage;
import com.temantani.land.service.domain.ports.output.repository.LandOutboxRepository;

@Component
public class LandOutboxHelper {

  private final ObjectMapper objectMapper;
  private final LandOutboxRepository landOutboxRepository;

  public LandOutboxHelper(ObjectMapper objectMapper, LandOutboxRepository landOutboxRepository) {
    this.objectMapper = objectMapper;
    this.landOutboxRepository = landOutboxRepository;
  }

  @Transactional(readOnly = true)
  public Optional<List<LandRegistetedOutboxMessage>> getOutboxMessages(OutboxStatus... outboxStatus) {
    return landOutboxRepository.findByOutboxStatuses(outboxStatus);
  }

  @Transactional
  public void updateOutboxMessage(LandRegistetedOutboxMessage outbox, OutboxStatus status) {
    outbox.setOutboxStatus(status);
    outbox.setProcessedAt(ZonedDateTime.now(ZoneId.of(TIMEZONE)));

    save(outbox);
  }

  @Transactional
  public void createOutboxMessage(LandRegisteredEventPayload eventPayload) {

    LandRegistetedOutboxMessage outbox = LandRegistetedOutboxMessage.builder()
        .id(UUID.randomUUID())
        .payload(writePayload(eventPayload))
        .outboxStatus(STARTED)
        .createdAt(ZonedDateTime.now(ZoneId.of(TIMEZONE)))
        .build();

    save(outbox);
  }

  private String writePayload(LandRegisteredEventPayload eventPayload) {
    try {
      return objectMapper.writeValueAsString(eventPayload);
    } catch (Exception e) {
      throw new LandDomainException("Failed to write payload", e);
    }
  }

  private void save(LandRegistetedOutboxMessage outbox) {
    if (landOutboxRepository.save(outbox) == null) {
      throw new LandDomainException("Failed to save outbox message");
    }
  }

}
