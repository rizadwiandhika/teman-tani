package com.temantani.user.service.domain.outbox.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserEventPayload {

  private String type;
  private UUID userId;
  private String name;
  private String email;
  private String profilePicture;
  private String phoneNumber;
  private String activatedRole;
  private String roles;
  private String bank;
  private String bankAccountNumber;
  private String bankAccountHolderName;
  private String street;
  private String city;
  private String postalCode;

}
