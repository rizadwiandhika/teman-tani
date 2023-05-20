package com.temantani.user.service.domain.event;

import java.time.ZonedDateTime;

import com.temantani.user.service.domain.entity.Admin;

public class AdminRegisteredEvent extends AdminEvent {

  public AdminRegisteredEvent(Admin admin, ZonedDateTime createdAt) {
    super(admin, createdAt);
  }

}
