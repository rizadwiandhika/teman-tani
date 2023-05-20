package com.temantani.user.service.domain.entity;

import java.util.List;
import java.util.UUID;

import com.temantani.domain.entity.AggregateRoot;
import com.temantani.domain.valueobject.AdminRole;
import com.temantani.domain.valueobject.UserId;
import com.temantani.user.service.domain.exception.UserDomainException;
import com.temantani.user.service.domain.service.UserPasswordEncoder;

public class Admin extends AggregateRoot<UserId> {

  private final String email;
  private AdminRole role;

  private String name;
  private String password;
  private String phoneNumber;

  public void validateRegistration(Admin initiator, List<String> existingEmails) {
    if (initiator == null) {
      throw new UserDomainException("Initiator cannot be null");
    }

    if (initiator.getRole() != AdminRole.ADMIN_SUPER) {
      throw new UserDomainException("Only super admin can create admin");
    }

    validateNotEmpty(List.of(email, name, password, phoneNumber));

    if (existingEmails == null || existingEmails.contains(email)) {
      throw new UserDomainException("Email already registered");
    }

    if (role == AdminRole.ADMIN_SUPER) {
      throw new UserDomainException("Cannot create super admin");
    }
  }

  public void register(UserPasswordEncoder passwordEncoder) {
    super.setId(new UserId(UUID.randomUUID()));
    password = passwordEncoder.encode(password);
  }

  private void validateNotEmpty(List<String> mustExistStringFields) {
    for (String field : mustExistStringFields) {
      if (field == null || field.trim().isEmpty()) {
        throw new UserDomainException("Admin email, name, phone, and password cannot be empty");
      }
    }
  }

  // Getters
  public String getEmail() {
    return email;
  }

  public AdminRole getRole() {
    return role;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  // Builder pattern
  private Admin(Builder builder) {
    super.setId(builder.id);
    this.email = builder.email;
    this.role = builder.role;
    this.name = builder.name;
    this.password = builder.password;
    this.phoneNumber = builder.phoneNumber;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private UserId id;
    private String email;
    private AdminRole role;
    private String name;
    private String password;
    private String phoneNumber;

    public Builder id(UserId id) {
      this.id = id;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder role(AdminRole role) {
      this.role = role;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Builder phoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
      return this;
    }

    public Admin build() {
      return new Admin(this);
    }
  }

}
