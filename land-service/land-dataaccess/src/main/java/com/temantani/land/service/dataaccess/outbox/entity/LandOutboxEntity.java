package com.temantani.land.service.dataaccess.outbox.entity;

import java.time.ZonedDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.temantani.domain.outbox.OutboxStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "land_outbox")
public class LandOutboxEntity {
  @Id
  private UUID id;

  @Version
  private int version;

  @Enumerated(EnumType.STRING)
  private OutboxStatus outboxStatus;

  private String payload;
  private ZonedDateTime createdAt;
  private ZonedDateTime processedAt;
}
