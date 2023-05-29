package com.temantani.project.service.domain.entity;

import com.temantani.domain.entity.AggregateRoot;
import com.temantani.domain.valueobject.UserId;

public class Manager extends AggregateRoot<UserId> {

  private final String email;
  private final String name;

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public Manager(UserId managerId, String email, String name) {
    super.setId(managerId);
    this.email = email;
    this.name = name;
  }

}
