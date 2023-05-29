package com.temantani.domain.outbox;

import java.time.ZonedDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class OutboxMessage {

  private final UUID id;
  private final String payload;
  private final Integer version;
  private final ZonedDateTime createdAt;

  private OutboxStatus outboxStatus;
  private ZonedDateTime processedAt;

  public void setOutboxStatus(OutboxStatus outboxStatus) {
    this.outboxStatus = outboxStatus;
  }

  public void setProcessedAt(ZonedDateTime processedAt) {
    this.processedAt = processedAt;
  }

}
