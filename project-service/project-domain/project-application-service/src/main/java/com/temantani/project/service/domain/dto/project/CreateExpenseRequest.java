package com.temantani.project.service.domain.dto.project;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateExpenseRequest {

  @NotNull
  private String name;

  @NotNull
  private String description;

  @NotNull
  private BigDecimal amount;

  // private ZonedDateTime createdAt;

  private String invoiceUrl;

}
