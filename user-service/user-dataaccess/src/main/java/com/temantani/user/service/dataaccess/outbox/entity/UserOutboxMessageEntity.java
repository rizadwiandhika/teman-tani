package com.temantani.user.service.dataaccess.outbox.entity;

import java.time.ZonedDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Type;

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
@Table(name = "user_outbox")
public class UserOutboxMessageEntity {
  @Id
  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID id;

  @Enumerated(EnumType.STRING)
  private OutboxStatus outboxStatus;

  @Version
  private int version;

  private String payload;
  private ZonedDateTime createdAt;
  private ZonedDateTime processedAt;
}
