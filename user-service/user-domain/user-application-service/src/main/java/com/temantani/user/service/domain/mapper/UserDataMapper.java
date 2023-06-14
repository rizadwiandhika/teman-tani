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
import com.temantani.user.service.domain.dto.roleactivation.RoleActivationResponse;
import com.temantani.user.service.domain.dto.track.UserDetailTrackResponse;
import com.temantani.user.service.domain.entity.Admin;
import com.temantani.user.service.domain.entity.User;
import com.temantani.user.service.domain.event.AdminRegisteredEvent;
import com.temantani.user.service.domain.event.UserProfileUpdatedEvent;
import com.temantani.user.service.domain.event.UserRoleActivatedEvent;
import com.temantani.user.service.domain.outbox.model.UserEventPayload;

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
        .userId(user.getId().getValue().toString())
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
        .address(user.getAddress() == null ? null
            : new com.temantani.user.service.domain.dto.common.Address(
                user.getAddress().getStreet(), user.getAddress().getCity(), user.getAddress().getPostalCode()))
        .bankAccount(user.getBankAccount() == null ? null
            : new com.temantani.user.service.domain.dto.common.BankAccount(
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

  public RoleActivationResponse userToRoleActivationResponse(User user, String message, String activatedRole) {
    return RoleActivationResponse.builder()
        .message(message)
        .activatedRole(activatedRole)
        .roles(user.getRoles().stream().map(UserRole::name).collect(Collectors.toList()))
        .build();
  }

  public UserEventPayload userRoleActivatedEventToUserOutboxMessage(UserRoleActivatedEvent event) {
    User user = event.getUser();
    return UserEventPayload.builder()
        .type("ROLE_ACTIVATED")
        .userId(user.getId().getValue())
        .name(user.getName())
        .email(user.getEmail())
        .phoneNumber(user.getPhoneNumber())
        .profilePicture(user.getProfilePictureUrl())
        .activatedRole(event.getActivatedRole().name())
        .roles(String.join(",", user.getRoles().stream().map(UserRole::name).collect(Collectors.toList())))
        .bank(user.getBankAccount() == null ? "" : user.getBankAccount().getBank())
        .bankAccountNumber(user.getBankAccount() == null ? "" : user.getBankAccount().getAccountNumber())
        .bankAccountHolderName(user.getBankAccount() == null ? "" : user.getBankAccount().getAccountHolderName())
        .street(user.getAddress() == null ? "" : user.getAddress().getStreet())
        .city(user.getAddress() == null ? "" : user.getAddress().getCity())
        .postalCode(user.getAddress() == null ? "" : user.getAddress().getPostalCode())
        .build();
  }

  public UserEventPayload userProfileUpdatedEventToUserEventPayload(UserProfileUpdatedEvent event) {
    User user = event.getUser();
    return UserEventPayload.builder()
        .type("PROFILE_UPDATED")
        .userId(user.getId().getValue())
        .name(user.getName())
        .email(user.getEmail())
        .phoneNumber(user.getPhoneNumber())
        .profilePicture(user.getProfilePictureUrl())
        .activatedRole("")
        .roles(String.join(",", user.getRoles().stream().map(UserRole::name).collect(Collectors.toList())))
        .bank(user.getBankAccount() == null ? "" : user.getBankAccount().getBank())
        .bankAccountNumber(user.getBankAccount() == null ? "" : user.getBankAccount().getAccountNumber())
        .bankAccountHolderName(user.getBankAccount() == null ? "" : user.getBankAccount().getAccountHolderName())
        .street(user.getAddress() == null ? "" : user.getAddress().getStreet())
        .city(user.getAddress() == null ? "" : user.getAddress().getCity())
        .postalCode(user.getAddress() == null ? "" : user.getAddress().getPostalCode())
        .build();
  }

  public UserEventPayload adminRegisteredEventToUserEventPayload(AdminRegisteredEvent event) {
    Admin admin = event.getAdmin();
    return UserEventPayload.builder()
        .type("ROLE_ACTIVATED")
        .userId(admin.getId().getValue())
        .email(admin.getEmail())
        .phoneNumber(admin.getPhoneNumber())
        .activatedRole(admin.getRole().name())
        .roles(admin.getRole().name())
        .name(admin.getName())
        .build();
  }

}
