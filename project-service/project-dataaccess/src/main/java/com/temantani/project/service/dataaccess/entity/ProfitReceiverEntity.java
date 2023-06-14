package com.temantani.project.service.dataaccess.entity;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
// @IdClass(ProfitReceiverEntityId.class)
@Table(name = "profit_receivers")
public class ProfitReceiverEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // @Id
  @Enumerated(EnumType.STRING)
  private DistributionType type;

  // @Id
  @ManyToOne
  @JoinColumn(name = "PROJECT_ID")
  private ProjectEntity project;

  // @Id
  private UUID receiverId;
  private BigDecimal devidend;

}
