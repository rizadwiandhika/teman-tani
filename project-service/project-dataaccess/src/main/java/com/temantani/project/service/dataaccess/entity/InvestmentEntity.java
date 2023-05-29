package com.temantani.project.service.dataaccess.entity;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

}
