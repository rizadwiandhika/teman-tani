package com.temantani.project.service.domain.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InvestmentObject {

  private UUID id;
  private UUID projectId;
  private String status;

}
