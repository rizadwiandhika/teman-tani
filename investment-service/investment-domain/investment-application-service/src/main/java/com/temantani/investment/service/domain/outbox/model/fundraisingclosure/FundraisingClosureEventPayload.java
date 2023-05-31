package com.temantani.investment.service.domain.outbox.model.fundraisingclosure;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FundraisingClosureEventPayload {
  private String status;
  private String projectId;
  private List<InvestmentPayload> investments;
}
