package com.temantani.project.service.domain.dto.project;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import com.temantani.domain.valueobject.ProjectStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateProjectResponse {
  private UUID projectId;
  private UUID managerId;
  private UUID landId;
  private ProjectStatus status;
  private String description;
  private String harvest;
  private Integer workerNeeds;
  private BigDecimal fundaisingTarget;
  private ZonedDateTime fundaisingDeadline;
  private ZonedDateTime estimatedFinished;
}
