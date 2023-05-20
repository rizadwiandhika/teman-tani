package com.temantani.land.service.domain.entity;

import com.temantani.domain.entity.AggregateRoot;
import com.temantani.domain.valueobject.UserId;

public class Borrower extends AggregateRoot<UserId> {

  private String email;
  private String name;
  private String profilePictureUrl;

  private Borrower(Builder builder) {
    super.setId(builder.id);
    this.email = builder.email;
    this.name = builder.name;
    this.profilePictureUrl = builder.profilePictureUrl;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public String getProfilePictureUrl() {
    return profilePictureUrl;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    UserId id;
    String email;
    String name;
    String profilePictureUrl;

    private Builder() {
    }

    public Builder id(UserId id) {
      this.id = id;
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

    public Builder profilePictureUrl(String profilePictureUrl) {
      this.profilePictureUrl = profilePictureUrl;
      return this;
    }

    public Borrower build() {
      return new Borrower(this);
    }
  }

}
