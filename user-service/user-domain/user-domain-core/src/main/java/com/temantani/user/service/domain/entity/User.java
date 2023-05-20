package com.temantani.user.service.domain.entity;

import java.util.List;
import java.util.UUID;

import com.temantani.domain.entity.AggregateRoot;
import com.temantani.domain.valueobject.UserRole;
import com.temantani.domain.valueobject.Address;
import com.temantani.domain.valueobject.BankAccount;
import com.temantani.domain.valueobject.UserId;
import com.temantani.user.service.domain.exception.UserDomainException;
import com.temantani.user.service.domain.service.UserPasswordEncoder;

public class User extends AggregateRoot<UserId> {

  private final String email;

  private String identityCardNumber;
  private String name;
  private String password;
  private String phoneNumber;
  private String profilePictureUrl;
  private String identityCardUrl;
  private List<UserRole> roles;
  private Address address;
  private BankAccount bankAccount;

  public void validateAndChangePassword(String oldPassword, String newPassword, UserPasswordEncoder passwordEncoder) {

    if (!passwordEncoder.matches(oldPassword, password)) {
      throw new UserDomainException("Old password is incorrect");
    }

    if (passwordEncoder.matches(newPassword, password)) {
      throw new UserDomainException("New password cannot be the same as old password");
    }

    password = passwordEncoder.encode(newPassword);
  }

  public void checkAccessibility(UserId trackerId) {
    if (!trackerId.equals(getId())) {
      throw new UserDomainException("User can only access their own profile");
    }
  }

  public void validateRegistration(List<String> existingEmails) {
    if (existingEmails.contains(email)) {
      throw new UserDomainException("User email already exists");
    }

    validateNotEmpty(List.of(
        this.email,
        this.name,
        this.password,
        this.phoneNumber));

    if (this.roles.size() != 0) {
      throw new UserDomainException("User roles must be empty");
    }

    if (!this.address.isComplete()) {
      throw new UserDomainException("User address must be complete");
    }
  }

  public void registerUser(UserPasswordEncoder passwordEncoder) {
    super.setId(new UserId(UUID.randomUUID()));
    roles.add(UserRole.BUYER);

    password = passwordEncoder.encode(password);
    // bankAccount = BankAccount.EMPTY;
  }

  public void validateUpdatedProfile(User initiator) {
    if (!initiator.getId().equals(getId())) {
      throw new UserDomainException("User can only update their own profile");
    }

    validateNotEmpty(List.of(
        this.email,
        this.name,
        this.password,
        this.phoneNumber));

    if (roles.size() < 1) {
      throw new UserDomainException("User must have at least one role");
    }

    if (!this.address.isComplete()) {
      throw new UserDomainException("User address must be complete");
    }

    validateRolesThatRequireBankAccount();

  }

  private void validateRolesThatRequireBankAccount() {
    // If user has any of roles that require to have bank account, then bank account
    // must not be empty
    List<UserRole> requiredBankAccountRoles = List.of(UserRole.INVESTOR, UserRole.LANDOWNER, UserRole.WORKER);
    if (roles.stream().anyMatch(requiredBankAccountRoles::contains)) {
      if (bankAccount == null || !bankAccount.isValid()) {
        throw new UserDomainException("User must have a bank account");
      }
    }
  }

  public void activateRole(User initiator, UserRole role) {
    if (!initiator.getId().equals(getId())) {
      throw new UserDomainException("User can only activate role for their own profile");
    }

    if (role.toString().startsWith("ADMIN_")) {
      throw new UserDomainException("Cannot activate admin role");
    }

    if (roles.contains(role)) {
      throw new UserDomainException("User already has role " + role.toString());
    }

    roles.add(role);

    validateRolesThatRequireBankAccount();
  }

  private void validateNotEmpty(List<String> mustExistStringFields) {
    for (String field : mustExistStringFields) {
      if (isStringEmpty(field)) {
        throw new UserDomainException("User email, name, phone, and password cannot be empty");
      }
    }
  }

  // Setter
  public void setName(String name) {
    this.name = name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public void setBankAccount(BankAccount bankAccount) {
    this.bankAccount = bankAccount;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setProfilePictureUrl(String profilePictureUrl) {
    this.profilePictureUrl = profilePictureUrl;
  }

  public void setIdentityCardNumber(String identityCardNumber) {
    this.identityCardNumber = identityCardNumber;
  }

  public void setIdentityCardUrl(String identityCardUrl) {
    this.identityCardUrl = identityCardUrl;
  }

  // Getter
  public String getEmail() {
    return email;
  }

  public String getIdentityCardNumber() {
    return identityCardNumber;
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

  public List<UserRole> getRoles() {
    return roles;
  }

  public Address getAddress() {
    return address;
  }

  public BankAccount getBankAccount() {
    return bankAccount;
  }

  public String getProfilePictureUrl() {
    return profilePictureUrl;
  }

  public String getIdentityCardUrl() {
    return identityCardUrl;
  }

  // utilities method
  private Boolean isStringEmpty(String string) {
    return string == null || string.isEmpty() || string.trim().isEmpty();
  }

  // builder pattern for User class
  private User(Builder builder) {
    super.setId(builder.id);

    this.identityCardNumber = builder.identityCardNumber;
    this.name = builder.name;
    this.email = builder.email;
    this.password = builder.password;
    this.phoneNumber = builder.phoneNumber;
    this.address = builder.address;
    this.bankAccount = builder.bankAccount;
    this.roles = builder.roles;
    this.profilePictureUrl = builder.profilePictureUrl;
    this.identityCardUrl = builder.identityCardUrl;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private UserId id;
    private String identityCardNumber;

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Address address;
    private BankAccount bankAccount;
    private List<UserRole> roles;
    private String profilePictureUrl;
    private String identityCardUrl;

    // public Builder(UserId id, String identityCardNumber) {
    // this.id = id;
    // this.identityCardNumber = identityCardNumber;
    // }

    // public Builder id(UserId id) {
    // this.id = id;
    // return this;
    // }

    public Builder() {
    }

    public Builder id(UserId userId) {
      this.id = userId;
      return this;
    }

    public Builder identityCardNumber(String identityCardNumber) {
      this.identityCardNumber = identityCardNumber;
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

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Builder phoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
      return this;
    }

    public Builder address(Address address) {
      this.address = address;
      return this;
    }

    public Builder bankAccount(BankAccount bankAccount) {
      this.bankAccount = bankAccount;
      return this;
    }

    public Builder roles(List<UserRole> roles) {
      this.roles = roles;
      return this;
    }

    public Builder profilePictureUrl(String profilePictureUrl) {
      this.profilePictureUrl = profilePictureUrl;
      return this;
    }

    public Builder identityCardUrl(String identityCardUrl) {
      this.identityCardUrl = identityCardUrl;
      return this;
    }

    public User build() {
      return new User(this);
    }
  }

}
