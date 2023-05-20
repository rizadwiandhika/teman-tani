package com.temantani.investment.service.dataaccess.postgresql.entity;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

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
  private UUID projectId;
  private UUID investorId;
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  private InvestmentStatus status;

  private String failureReasons;

}