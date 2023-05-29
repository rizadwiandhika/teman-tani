package com.temantani.project.service.dataaccess.entity;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.temantani.project.service.domain.valueobject.DistributionType;

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
public class ProfitDistributionDetailEntity {
  @Id
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "PROFIT_DISTRIBUTION_ID")
  private ProfitDistributionEntity profitDistribution;

  // private ProfitReceiver receiver;
  @Enumerated(EnumType.STRING)
  private DistributionType type;
  private BigDecimal devidend;
  private UUID receiverId;

  // private BankAccount bankAccount;
  private String bank;
  private String accountNumber;
  private String accountHolderName;

  private BigDecimal amount;
  private String transferProofUrl;
}
