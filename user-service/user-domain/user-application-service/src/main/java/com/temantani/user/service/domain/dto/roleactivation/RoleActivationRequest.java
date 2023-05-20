package com.temantani.user.service.domain.dto.roleactivation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.temantani.user.service.domain.dto.common.Address;
import com.temantani.user.service.domain.dto.common.BankAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RoleActivationRequest {

  private String identityCardUrl;

  @NotNull
  private String role;

  @NotNull
  private String identityCardNumber;

  @Valid
  @NotNull
  private BankAccount bankAccount;

  @Valid
  @NotNull
  private Address address;

}
