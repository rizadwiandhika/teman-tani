package com.temantani.investment.service.domain.entity;

import com.temantani.domain.entity.AggregateRoot;
import com.temantani.domain.valueobject.UserId;

public class Investor extends AggregateRoot<UserId> {

  private String name;
  private String email;
  private String profilePictureUrl;

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getProfilePictureUrl() {
    return profilePictureUrl;
  }

  // Builder pattern
  private Investor(Builder builder) {
    super.setId(builder.id);
    this.name = builder.name;
    this.email = builder.email;
    this.profilePictureUrl = builder.profilePictureUrl;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private UserId id;
    private String name;
    private String email;
    private String profilePictureUrl;

    public Builder id(UserId id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder profilePictureUrl(String profilePictureUrl) {
      this.profilePictureUrl = profilePictureUrl;
      return this;
    }

    public Investor build() {
      return new Investor(this);
    }
  }

}
