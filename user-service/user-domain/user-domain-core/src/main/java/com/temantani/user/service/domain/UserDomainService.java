package com.temantani.user.service.domain;

import java.util.List;

import com.temantani.domain.valueobject.UserRole;
import com.temantani.user.service.domain.entity.Admin;
import com.temantani.user.service.domain.entity.User;
import com.temantani.user.service.domain.event.AdminRegisteredEvent;
import com.temantani.user.service.domain.event.UserProfileUpdatedEvent;
import com.temantani.user.service.domain.event.UserRoleActivatedEvent;
import com.temantani.user.service.domain.service.UserPasswordEncoder;

public interface UserDomainService {

  UserRoleActivatedEvent validateAndRegisterUser(User user, List<String> existingEmail,
      UserPasswordEncoder passwordEncoder);

  UserRoleActivatedEvent activateRole(User initiator, User user, UserRole role);

  UserProfileUpdatedEvent updateProfile(User initiator, User updatedUser);

  AdminRegisteredEvent validateAndRegisterAdmin(Admin registrator, Admin newAdmin, List<String> existingEmail,
      UserPasswordEncoder passwordEncoder);
}
