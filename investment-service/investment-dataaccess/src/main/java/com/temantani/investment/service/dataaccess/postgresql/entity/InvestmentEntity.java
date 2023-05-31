package com.temantani.investment.service.dataaccess.postgresql.entity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.temantani.investment.service.domain.valueobject.InvestmentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "investments")
public class InvestmentEntity {

  @Id
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "PROJECT_ID")
  private ProjectEntity project;

  @Enumerated(EnumType.STRING)
  private InvestmentStatus status;

  @Version
  private Integer version;

  private UUID investorId;
  private BigDecimal amount;
  private ZonedDateTime expiredAt;
  private String failureReasons;

}