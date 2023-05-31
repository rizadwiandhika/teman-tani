package com.temantani.investment.service.dataaccess.postgresql.entity;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fundraising_closure_outbox")
public class FundraisingClosureOutboxEntity {
  @Id
  private UUID id;

  @Version
  private Integer version;

  @Enumerated(EnumType.STRING)
  private OutboxStatus outboxStatus;

  private String payload;
  private ZonedDateTime createdAt;
  private ZonedDateTime processedAt;
}
