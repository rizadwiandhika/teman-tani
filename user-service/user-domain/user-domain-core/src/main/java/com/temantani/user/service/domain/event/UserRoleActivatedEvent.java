package com.temantani.user.service.domain.event;

import java.time.ZonedDateTime;

import com.temantani.domain.valueobject.UserRole;
import com.temantani.user.service.domain.entity.User;

public class UserRoleActivatedEvent extends UserEvent {

  private final UserRole activatedRole;

  public UserRoleActivatedEvent(User user, UserRole activatedRole, ZonedDateTime createdAt) {
    super(user, createdAt);
    this.activatedRole = activatedRole;
  }

  public UserRole getActivatedRole() {
    return activatedRole;
  }

}
