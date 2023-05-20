package com.temantani.user.service.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.domain.dto.BasicResponse;
import com.temantani.domain.valueobject.UserRole;
import com.temantani.domain.valueobject.Address;
import com.temantani.domain.valueobject.BankAccount;
import com.temantani.domain.valueobject.UserId;
import com.temantani.user.service.domain.dto.registration.AdminRegistrationRequest;
import com.temantani.user.service.domain.dto.registration.UserRegistrationRequest;
import com.temantani.user.service.domain.dto.registration.UserRegistrationResponse;
import com.temantani.user.service.domain.dto.roleactivation.RoleActivationRequest;
import com.temantani.user.service.domain.dto.track.UserDetailTrackResponse;
import com.temantani.user.service.domain.dto.updateprofile.UpdateProfileRequest;
import com.temantani.user.service.domain.entity.Admin;
import com.temantani.user.service.domain.entity.User;
import com.temantani.user.service.domain.event.AdminRegisteredEvent;
import com.temantani.user.service.domain.event.UserProfileUpdatedEvent;
import com.temantani.user.service.domain.event.UserRegisteredEvent;
import com.temantani.user.service.domain.event.UserRoleActivatedEvent;
import com.temantani.user.service.domain.exception.UserDomainException;
import com.temantani.user.service.domain.mapper.UserDataMapper;
import com.temantani.user.service.domain.ports.input.service.UserApplicationService;
import com.temantani.user.service.domain.ports.output.repository.AdminRepository;
import com.temantani.user.service.domain.ports.output.repository.UserRepository;
import com.temantani.user.service.domain.service.UserPasswordEncoder;

@Component
public class UserApplicationServiceImpl implements UserApplicationService {

  private final UserDataMapper mapper;
  private final UserDomainService domainService;
  private final UserPasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final AdminRepository adminRepository;

  public UserApplicationServiceImpl(UserDataMapper mapper, UserDomainService domainService,
      UserPasswordEncoder passwordEncoder, UserRepository userRepository, AdminRepository adminRepository) {
    this.mapper = mapper;
    this.domainService = domainService;
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.adminRepository = adminRepository;
  }

  @Override
  @Transactional
  public UserRegistrationResponse registerUser(UserRegistrationRequest request) {
    User user = mapper.userRegistrationRequestToUser(request);

    List<String> exisitingEmails = getExistingEmails(user.getEmail());
    UserRegisteredEvent event = domainService.validateAndRegisterUser(user, exisitingEmails, passwordEncoder);

    userRepository.save(event.getUser());

    // TODO: save to outbox table

    return mapper.userToUserRegistrationResponse(user, "User registered successfully");
  }

  @Override
  @Transactional
  public BasicResponse registerAdmin(UserId registratorId, AdminRegistrationRequest request) {
    Admin admin;
    try {
      admin = mapper.adminRegistrationRequestToAdmin(request);
    } catch (Exception e) {
      throw new UserDomainException("Invalid role: " + request.getRole());
    }

    Admin registrator = findAdminByIdOrThrow(registratorId);
    List<String> existingEmails = getExistingEmails(admin.getEmail());

    AdminRegisteredEvent event = domainService.validateAndRegisterAdmin(registrator, admin, existingEmails,
        passwordEncoder);

    adminRepository.save(event.getAdmin());

    return BasicResponse.builder().message("Admin registered successfully").build();
  }

  @Override
  @Transactional
  public BasicResponse updateProfile(UserId initiatorId, UserId userId, UpdateProfileRequest request) {
    User initiator = findUserByIdOrThrow(initiatorId);
    User user = findUserByIdOrThrow(userId);

    UserProfileUpdatedEvent event = domainService.updateProfile(initiator, getUpdatedUserProfile(user, request));
    userRepository.save(event.getUser());

    // TODO: save to outbox table

    return BasicResponse.builder().message("User successfully updated").build();
  }

  @Override
  @Transactional
  public void activateRole(UserId initiatorId, UserId userId, RoleActivationRequest request) {
    User initiator = findUserByIdOrThrow(initiatorId);
    User user = getUpdatedUserForRoleActivation(findUserByIdOrThrow(userId), request);

    UserRole role;

    try {
      role = UserRole.valueOf(request.getRole().toUpperCase());
    } catch (Exception e) {
      throw new UserDomainException("Invalid role: " + request.getRole());
    }

    UserRoleActivatedEvent event = domainService.activateRole(initiator, user, role);

    // TODO: save to outbox table

    userRepository.save(event.getUser());

  }

  @Override
  @Transactional(readOnly = true)
  public UserDetailTrackResponse trackUserDetail(UserId trackerId, UserId userId) {
    User user = findUserByIdOrThrow(userId);
    user.checkAccessibility(trackerId);

    return mapper.userToUserDetailTrackResponse(user);
  }

  @Override
  @Transactional
  public BasicResponse changePassword(UserId userId, String oldPassword, String newPassword) {
    User user = findUserByIdOrThrow(userId);
    user.validateAndChangePassword(oldPassword, newPassword, passwordEncoder);
    userRepository.save(user);

    return BasicResponse.builder().message("Password changed successfully").build();
  }

  private User findUserByIdOrThrow(UserId userId) {
    Optional<User> userOp = userRepository.findById(userId);
    if (userOp.isEmpty()) {
      throw new UserDomainException("User not found with email: " + userId.getValue().toString());
    }
    return userOp.get();
  }

  private Admin findAdminByIdOrThrow(UserId userId) {
    Optional<Admin> adminOp = adminRepository.findById(userId);
    if (adminOp.isEmpty()) {
      throw new UserDomainException("User not found with email: " + userId.getValue().toString());
    }
    return adminOp.get();
  }

  private List<String> getExistingEmails(String email) {
    List<String> emails = new ArrayList<>();

    emails.add(userRepository.findEmail(email));
    emails.add(adminRepository.findEmail(email));

    return emails;
  }

  private User getUpdatedUserProfile(User user, UpdateProfileRequest request) {
    user.setName(request.getName());
    user.setPhoneNumber(request.getPhoneNumber());
    user.setBankAccount(getUpdatedBank(user, request.getBankAccount()));
    user.setAddress(getUpdatedAddress(user, request.getAddress()));

    return user;
  }

  private User getUpdatedUserForRoleActivation(User user, RoleActivationRequest request) {
    user.setIdentityCardUrl(request.getIdentityCardUrl());
    user.setIdentityCardNumber(request.getIdentityCardNumber());
    user.setBankAccount(getUpdatedBank(user, request.getBankAccount()));
    user.setAddress(getUpdatedAddress(user, request.getAddress()));

    return user;
  }

  private Address getUpdatedAddress(User user, com.temantani.user.service.domain.dto.common.Address addressDto) {
    Address address = mapper.addressDtoToAddress(addressDto);
    // address.setId(user.getAddress().getId());
    return address;
  }

  private BankAccount getUpdatedBank(User user, com.temantani.user.service.domain.dto.common.BankAccount bankDto) {
    BankAccount bankAccount = mapper.bankDtoRequestToBankAccount(bankDto);
    // bankAccount.setId(user.getBankAccount().getId());
    return bankAccount;
  }

}
