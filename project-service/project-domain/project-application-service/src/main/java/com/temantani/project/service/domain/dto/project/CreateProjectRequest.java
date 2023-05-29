package com.temantani.project.service.domain.dto.project;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateProjectRequest {

  @NotNull
  private UUID landId;

  @NotNull
  private String description;

  @NotNull
  private String harvest;

  @NotNull
  private Integer workerNeeds;

  @NotNull
  private BigDecimal fundaisingTarget;

  @NotNull
  private ZonedDateTime fundaisingDeadline;

  @NotNull
  private ZonedDateTime estimatedFinished;

}
