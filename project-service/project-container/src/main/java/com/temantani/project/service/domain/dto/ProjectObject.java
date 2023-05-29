package com.temantani.project.service.domain.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProjectObject {

  private UUID id;
  private String status;
  private List<InvestmentObject> investments;

}
