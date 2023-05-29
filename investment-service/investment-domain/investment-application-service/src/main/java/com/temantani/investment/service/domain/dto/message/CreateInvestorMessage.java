package com.temantani.investment.service.domain.dto.message;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateInvestorMessage {
  private UUID userId;
  private String name;
  private String email;
  private String profilePictureUrl;
}
