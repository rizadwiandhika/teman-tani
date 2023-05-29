package com.temantani.project.service.domain.dto.profitdistribution;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import com.temantani.project.service.domain.valueobject.DistributionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TrackProfitDistributionResponse {

  private UUID id;
  private UUID projectId;
  private ZonedDateTime distributedAt;
  private DistributionStatus status;
  private List<ProfitDistributionDetailDto> details;

}
