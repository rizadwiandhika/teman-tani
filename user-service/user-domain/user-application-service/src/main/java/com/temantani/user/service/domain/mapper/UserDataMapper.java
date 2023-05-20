package com.temantani.user.service.domain.mapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.Address;
import com.temantani.domain.valueobject.AdminRole;
import com.temantani.domain.valueobject.BankAccount;
import com.temantani.domain.valueobject.UserRole;
import com.temantani.user.service.domain.dto.registration.AdminRegistrationRequest;
import com.temantani.user.service.domain.dto.registration.UserRegistrationRequest;
import com.temantani.user.service.domain.dto.registration.UserRegistrationResponse;
import com.temantani.user.service.domain.dto.track.UserDetailTrackResponse;
import com.temantani.user.service.domain.entity.Admin;
import com.temantani.user.service.domain.entity.User;

@Component
public class UserDataMapper {

  public User userRegistrationRequestToUser(UserRegistrationRequest req) {

    Address address = new Address(
        req.getAddress().getStreet(),
        req.getAddress().getCity(),
        req.getAddress().getPostalCode());

    return User.builder()
        .email(req.getEmail())
        .name(req.getName())
        .password(req.getPassword())
        .phoneNumber(req.getPhoneNumber())
        .roles(new ArrayList<>())
        .address(address)
        .build();
  }

  public UserRegistrationResponse userToUserRegistrationResponse(User user, String message) {
    return UserRegistrationResponse.builder()
        .email(user.getEmail())
        .message(message)
        .build();
  }

  public Address addressDtoToAddress(com.temantani.user.service.domain.dto.common.Address address) {
    return new Address(
        address.getStreet(),
        address.getCity(),
        address.getPostalCode());
  }

  public BankAccount bankDtoRequestToBankAccount(com.temantani.user.service.domain.dto.common.BankAccount bank) {
    return new BankAccount(
        bank.getBank(),
        bank.getAccountNumber(),
        bank.getAccountHolderName());
  }

  public UserDetailTrackResponse userToUserDetailTrackResponse(User user) {
    return UserDetailTrackResponse.builder()
        .id(user.getId().getValue().toString())
        .email(user.getEmail())
        .name(user.getName())
        .phoneNumber(user.getPhoneNumber())
        .profilePictureUrl(user.getProfilePictureUrl())
        .identityCardNumber(user.getIdentityCardNumber())
        .identityCardUrl(user.getIdentityCardUrl())
        .roles(user.getRoles().stream().map(UserRole::name).collect(Collectors.toList()))
        .address(new com.temantani.user.service.domain.dto.common.Address(
            user.getAddress().getStreet(), user.getAddress().getCity(), user.getAddress().getPostalCode()))
        .bankAccount(new com.temantani.user.service.domain.dto.common.BankAccount(
            user.getBankAccount().getBank(), user.getBankAccount().getAccountNumber(),
            user.getBankAccount().getAccountHolderName()))
        .build();
  }

  public Admin adminRegistrationRequestToAdmin(AdminRegistrationRequest request) {
    return Admin.builder()
        .name(request.getName())
        .email(request.getEmail())
        .password(request.getPassword())
        .phoneNumber(request.getPhoneNumber())
        .role(AdminRole.valueOf(request.getRole()))
        .build();
  }

}
