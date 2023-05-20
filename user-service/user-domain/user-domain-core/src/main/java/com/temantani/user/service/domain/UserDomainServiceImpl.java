package com.temantani.user.service.domain;

import static com.temantani.domain.DomainConstant.TIMEZONE;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import com.temantani.domain.valueobject.UserRole;
import com.temantani.user.service.domain.entity.Admin;
import com.temantani.user.service.domain.entity.User;
import com.temantani.user.service.domain.event.AdminRegisteredEvent;
import com.temantani.user.service.domain.event.UserProfileUpdatedEvent;
import com.temantani.user.service.domain.event.UserRegisteredEvent;
import com.temantani.user.service.domain.event.UserRoleActivatedEvent;
import com.temantani.user.service.domain.service.UserPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserDomainServiceImpl implements UserDomainService {

  @Override
  public UserRegisteredEvent validateAndRegisterUser(User user, List<String> existingEmails,
      UserPasswordEncoder passwordEncoder) {
    user.validateRegistration(existingEmails);
    user.registerUser(passwordEncoder);

    log.info("[UserDomainServiceImpl] registered user email: {}", user.getEmail());

    return new UserRegisteredEvent(user, ZonedDateTime.now(ZoneId.of(TIMEZONE)));
  }

  @Override
  public UserProfileUpdatedEvent updateProfile(User initiator, User updatedUser) {
    updatedUser.validateUpdatedProfile(initiator);

    log.info("User profile updated for id: {}", updatedUser.getId().getValue().toString());

    return new UserProfileUpdatedEvent(updatedUser, ZonedDateTime.now(ZoneId.of(TIMEZONE)));
  }

  @Override
  public UserRoleActivatedEvent activateRole(User initiator, User user, UserRole role) {
    user.activateRole(initiator, role);
    log.info("Role is activated for user id: {}", user.getId().getValue().toString());

    return new UserRoleActivatedEvent(user, role, ZonedDateTime.now(ZoneId.of(TIMEZONE)));
  }

  @Override
  public AdminRegisteredEvent validateAndRegisterAdmin(Admin registrator, Admin newAdmin, List<String> existingEmails,
      UserPasswordEncoder passwordEncoder) {
    newAdmin.validateRegistration(registrator, existingEmails);
    newAdmin.register(passwordEncoder);

    return new AdminRegisteredEvent(newAdmin, ZonedDateTime.now(ZoneId.of(TIMEZONE)));
  }

}
