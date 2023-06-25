package com.temantani.kafka.user.json.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserJsonModel {
  @JsonProperty
  private String type;

  @JsonProperty
  private String userId;

  @JsonProperty
  private String name;

  @JsonProperty
  private String phoneNumber;

  @JsonProperty
  private String email;

  @JsonProperty
  private String profilePictureUrl;

  @JsonProperty
  private String activatedRole;

  @JsonProperty
  private String roles;

  @JsonProperty
  private String bank;

  @JsonProperty
  private String bankAccountNumber;

  @JsonProperty
  private String bankAccountHolderName;

  @JsonProperty
  private String street;

  @JsonProperty
  private String city;

  @JsonProperty
  private String postalCode;

}
