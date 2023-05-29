package com.temantani.land.service.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateBorrowerMessage {

  private String borrowerId;
  private String email;
  private String name;
  private String phoneNumber;
  private String profilePictureUrl;

}