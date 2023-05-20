package com.temantani.user.service.domain.dto.track;

import java.util.List;

import com.temantani.user.service.domain.dto.common.Address;
import com.temantani.user.service.domain.dto.common.BankAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailTrackResponse {

  private String id;
  private String email;
  private String name;
  private String phoneNumber;
  private String profilePictureUrl;
  private String identityCardNumber;
  private String identityCardUrl;
  private List<String> roles;
  private Address address;
  private BankAccount bankAccount;

}
