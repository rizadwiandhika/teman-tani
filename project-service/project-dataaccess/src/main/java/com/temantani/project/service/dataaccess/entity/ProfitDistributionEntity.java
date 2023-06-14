package com.temantani.project.service.dataaccess.entity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.temantani.project.service.domain.valueobject.DistributionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profit_distributions")
public class ProfitDistributionEntity {
  @Id
  private UUID id;

  @OneToMany(mappedBy = "profitDistribution", cascade = CascadeType.ALL)
  private List<ProfitDistributionDetailEntity> details;

  @Enumerated(EnumType.STRING)
  private DistributionStatus status;

  private UUID projectId;
  private UUID managerId;
  private BigDecimal projectProfit;
  private ZonedDateTime distributedAt;
}
