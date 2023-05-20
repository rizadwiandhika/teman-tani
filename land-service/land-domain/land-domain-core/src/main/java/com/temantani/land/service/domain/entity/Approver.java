package com.temantani.land.service.domain.entity;

import com.temantani.domain.entity.AggregateRoot;
import com.temantani.domain.valueobject.UserId;

public class Approver extends AggregateRoot<UserId> {

  private String email;
  private String name;

  private Approver(Builder builder) {
    super.setId(builder.approverId);
    this.email = builder.email;
    this.name = builder.name;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    UserId approverId;
    String email;
    String name;

    private Builder() {
    }

    public Builder approverId(UserId userId) {
      this.approverId = userId;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Approver build() {
      return new Approver(this);
    }
  }

}
