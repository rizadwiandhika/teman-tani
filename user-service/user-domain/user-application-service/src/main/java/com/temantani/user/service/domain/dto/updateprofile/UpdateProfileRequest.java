package com.temantani.user.service.domain.dto.updateprofile;

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
public class UpdateProfileRequest {

  @NotNull
  private String name;

  @NotNull
  private String phoneNumber;

  @NotNull
  @Valid
  private Address address;

  private BankAccount bankAccount;

}
