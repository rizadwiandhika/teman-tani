package com.temantani.project.service.domain.dto.message.user;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ManagerRegisteredMessage {

  private UUID managerId;
  private String email;
  private String name;

}
