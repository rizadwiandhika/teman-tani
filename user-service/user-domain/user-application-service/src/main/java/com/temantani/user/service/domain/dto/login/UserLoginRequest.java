package com.temantani.user.service.domain.dto.login;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserLoginRequest {

  @NotNull(message = "is required")
  private String email;

  @NotNull(message = "is required")
  private String password;

}
