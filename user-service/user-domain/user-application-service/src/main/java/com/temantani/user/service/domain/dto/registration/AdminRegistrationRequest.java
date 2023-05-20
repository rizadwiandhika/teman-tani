package com.temantani.user.service.domain.dto.registration;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AdminRegistrationRequest {

  @NotNull
  private String email;

  @NotNull
  private String password;

  @NotNull
  private String name;

  @NotNull
  private String phoneNumber;

  @NotNull
  private String role;
}
