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
import javax.persistence.Version;

import com.temantani.domain.valueobject.ProjectStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class ProjectEntity {

  @Id
  private UUID id;

  @Enumerated(EnumType.STRING)
  private ProjectStatus status;

  @Version
  private Integer version;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
  private List<ProfitReceiverEntity> profitReceivers;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
  private List<ExpenseEntity> expenses;

  private UUID landId;
  private UUID managerId;
  private String description;
  private String details;
  private String harvest;
  private Integer workerNeeds;
  private BigDecimal fundraisingTarget;
  private ZonedDateTime fundraisingDeadline;
  private ZonedDateTime estimatedFinished;
  private BigDecimal collectedFunds;
  private BigDecimal income;
  private BigDecimal distributedIncome;
  private ZonedDateTime createdAt;
  private ZonedDateTime executedAt;
  private ZonedDateTime finishedAt;
  private String failureMessages;
}
