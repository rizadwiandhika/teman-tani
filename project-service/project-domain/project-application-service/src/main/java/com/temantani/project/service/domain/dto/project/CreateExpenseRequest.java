package com.temantani.project.service.domain.dto.project;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

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

  @NotNull
  private ZonedDateTime createdAt;

  private String invoiceUrl;

}
