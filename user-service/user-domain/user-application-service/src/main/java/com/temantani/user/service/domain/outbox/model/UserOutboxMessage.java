package com.temantani.user.service.domain.outbox.model;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.temantani.domain.outbox.OutboxStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserOutboxMessage {

  private final UUID id;
  private final String payload;
  private final int version;
  private OutboxStatus outboxStatus;
  private ZonedDateTime createdAt;
  private ZonedDateTime processedAt;

  public void setOutboxStatus(OutboxStatus outboxStatus) {
    this.outboxStatus = outboxStatus;
  }

  public void setCreatedAt(ZonedDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public void setProcessedAt(ZonedDateTime processedAt) {
    this.processedAt = processedAt;
  }

}
