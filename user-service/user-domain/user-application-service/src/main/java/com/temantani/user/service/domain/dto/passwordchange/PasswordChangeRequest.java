package com.temantani.user.service.domain.dto.passwordchange;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeRequest {

  @NotNull
  private String currentPassword;

  @NotNull
  private String newPassword;

}
