package com.temantani.user.service.domain.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserLoginResponse {
  private String message;
  private String token;
}
