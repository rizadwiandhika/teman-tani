package com.temantani.project.service.domain.dto.query;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.temantani.domain.valueobject.ProjectStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProjectData {
  private String id;
  private String description;
  private String harvest;
  private ProjectStatus status;
  private BigDecimal fundraisingTarget;
  private ZonedDateTime estimatedFinished;
  private BigDecimal collectedFunds;
  private ZonedDateTime createdAt;
  private ZonedDateTime executedAt;
  private ZonedDateTime finishedAt;
}
