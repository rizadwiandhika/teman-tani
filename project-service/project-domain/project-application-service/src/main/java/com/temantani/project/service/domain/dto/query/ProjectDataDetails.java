package com.temantani.project.service.domain.dto.query;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import com.temantani.domain.valueobject.ProjectStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProjectDataDetails {
  private String id;
  private String landId;
  private String managerId;
  private String description;
  private Map<String, String> details;
  private String harvest;
  private ProjectStatus status;
  private Integer workerNeeds;
  private BigDecimal fundraisingTarget;
  private ZonedDateTime fundraisingDeadline;
  private ZonedDateTime estimatedFinished;
  private BigDecimal collectedFunds;
  private BigDecimal income;
  private BigDecimal distributedIncome;
  private List<ShareHolderData> shareHolders;
  private List<ExpenseData> expenses;
  private ZonedDateTime createdAt;
  private ZonedDateTime executedAt;
  private ZonedDateTime finishedAt;
  private List<String> failureMessages;
}
