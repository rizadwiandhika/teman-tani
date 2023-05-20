package com.temantani.user.service.domain.event;

import java.time.ZonedDateTime;

import com.temantani.domain.event.DomainEvent;
import com.temantani.user.service.domain.entity.Admin;

public class AdminEvent implements DomainEvent<Admin> {

  private final Admin admin;
  private final ZonedDateTime createdAt;

  public Admin getAdmin() {
    return admin;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public AdminEvent(Admin admin, ZonedDateTime createdAt) {
    this.admin = admin;
    this.createdAt = createdAt;
  }

}
