package com.temantani.land.service.domain.dto.query;

import java.time.ZonedDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProposalData {
  private ZonedDateTime approvedAt;
  private ZonedDateTime proposedAt;
  private List<String> reivisionMessages;
  private List<String> failureMessages;
}
