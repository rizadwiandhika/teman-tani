package com.temantani.user.service.domain.event;

import java.time.ZonedDateTime;

import com.temantani.user.service.domain.entity.User;

public class UserProfileUpdatedEvent extends UserEvent {

  public UserProfileUpdatedEvent(User user, ZonedDateTime createdAt) {
    super(user, createdAt);
  }

}
