package com.temantani.project.service.domain.dto.query;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ShareHolderData {
  private String userId;
  private String type;
  private BigDecimal devidend;
}
