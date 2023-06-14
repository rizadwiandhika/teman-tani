package com.temantani.project.service.domain.dto.query;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ExpenseData {
  private String id;
  private String projectId;
  private String name;
  private String description;
  private String invoiceUrl;
  private BigDecimal amount;
  private ZonedDateTime createdAt;
}
