package com.temantani.user.service.domain.outbox.scheduler;

import static com.temantani.domain.DomainConstant.TIMEZONE;
import static com.temantani.domain.outbox.OutboxStatus.STARTED;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.user.service.domain.exception.UserDomainException;
import com.temantani.user.service.domain.outbox.model.UserEventPayload;
import com.temantani.user.service.domain.outbox.model.UserOutboxMessage;
import com.temantani.user.service.domain.ports.output.repository.UserOutboxRepository;

@Component
public class UserOutboxHelper {

  private final UserOutboxRepository userOutboxRepository;
  private final ObjectMapper objectMapper;

  public UserOutboxHelper(UserOutboxRepository userOutboxRepository, ObjectMapper objectMapper) {
    this.userOutboxRepository = userOutboxRepository;
    this.objectMapper = objectMapper;
  }

  @Transactional(readOnly = true)
  public Optional<List<UserOutboxMessage>> getUserOutboxMessageByStatuses(OutboxStatus... status) {
    return userOutboxRepository.findByOutboxStatuses(status);
  }

  @Transactional
  public void createUserOutboxMessage(UserEventPayload eventPayload) {
    UserOutboxMessage outboxMessage = UserOutboxMessage.builder()
        .id(UUID.randomUUID())
        .outboxStatus(STARTED)
        .payload(getPayload(eventPayload))
        .createdAt(ZonedDateTime.now(ZoneId.of(TIMEZONE)))
        .build();

    save(outboxMessage);
  }

  @Transactional
  public void updateUserOutboxMessage(UserOutboxMessage outboxMessage, OutboxStatus status) {
    outboxMessage.setOutboxStatus(status);
    outboxMessage.setProcessedAt(ZonedDateTime.now(ZoneId.of(TIMEZONE)));

    save(outboxMessage);
  }

  public void deleteOutbxByStatus(OutboxStatus status) {
    userOutboxRepository.deleteByOutboxStatus(status);
  }

  private void save(UserOutboxMessage outboxMessage) {
    if (userOutboxRepository.save(outboxMessage) == null) {
      throw new UserDomainException("Unable to save user outbox message");
    }
  }

  private String getPayload(UserEventPayload eventPayload) {
    try {
      return objectMapper.writeValueAsString(eventPayload);
    } catch (JsonProcessingException e) {
      throw new UserDomainException("Unable to serialize payload user event payload", e);
    }
  }

}
