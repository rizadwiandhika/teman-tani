package com.temantani.user.service.domain.dto.registration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.temantani.user.service.domain.dto.common.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserRegistrationRequest {

  @NotNull
  private final String email;

  @NotNull
  private final String name;

  @NotNull
  private final String password;

  @NotNull
  private final String phoneNumber;

  @Valid
  @NotNull
  private final Address address;

}
