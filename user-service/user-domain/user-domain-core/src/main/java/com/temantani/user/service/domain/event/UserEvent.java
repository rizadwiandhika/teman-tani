package com.temantani.user.service.domain.event;

import java.time.ZonedDateTime;

import com.temantani.domain.event.DomainEvent;
import com.temantani.user.service.domain.entity.User;

public abstract class UserEvent implements DomainEvent<User> {

  private final User user;
  private final ZonedDateTime createdAt;

  public UserEvent(User user, ZonedDateTime createdAt) {
    this.user = user;
    this.createdAt = createdAt;
  }

  public User getUser() {
    return user;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

}
