package com.temantani.user.service.domain.dto.roleactivation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleActivationResponse {

  private String token;
  private String message;
  private List<String> roles;
  private String activatedRole;

}
