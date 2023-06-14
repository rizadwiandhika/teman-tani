package com.temantani.user.service.domain.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserRegistrationResponse {

  private final String userId;
  private final String email;
  private final String message;

}
