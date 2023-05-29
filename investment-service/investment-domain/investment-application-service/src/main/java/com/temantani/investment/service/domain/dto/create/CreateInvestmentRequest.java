package com.temantani.investment.service.domain.dto.create;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.temantani.domain.valueobject.UserId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvestmentRequest {

  @NotNull
  private String projectId;

  @NotNull
  private BigDecimal amount;

  private UserId investorId;

}
