package com.temantani.user.service.dataaccess.user.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.UserRole;
import com.temantani.domain.valueobject.Address;
import com.temantani.domain.valueobject.BankAccount;
import com.temantani.domain.valueobject.UserId;
import com.temantani.user.service.dataaccess.user.entity.AddressEntity;
import com.temantani.user.service.dataaccess.user.entity.AdminEntity;
import com.temantani.user.service.dataaccess.user.entity.BankAccountEntity;
import com.temantani.user.service.dataaccess.user.entity.RoleEntity;
import com.temantani.user.service.dataaccess.user.entity.UserEntity;
import com.temantani.user.service.domain.entity.Admin;
import com.temantani.user.service.domain.entity.User;

@Component
public class UserDataAccessMapper {

  public User userEntityToUser(UserEntity userEntity) {
    return User.builder()
        .id(new UserId(userEntity.getId()))
        .email(userEntity.getEmail())
        .identityCardNumber(userEntity.getIdentityCardNumber())
        .name(userEntity.getName())
        .password(userEntity.getPassword())
        .phoneNumber(userEntity.getPhoneNumber())
        .profilePictureUrl(userEntity.getProfilePictureUrl())
        .identityCardUrl(userEntity.getIdentityCardUrl())
        .bankAccount(bankAccountEntityToBankAccount(userEntity.getBankAccount()))
        .address(addressEntityToAddress(userEntity.getAddress()))
        .roles(roleEntitiesToRoles(userEntity.getRoles()))
        .build();
  }

  public UserEntity userToUserEntity(User user) {
    UserEntity userEntity = UserEntity.builder()
        .id(user.getId().getValue())
        .email(user.getEmail())
        .identityCardNumber(user.getIdentityCardNumber())
        .name(user.getName())
        .password(user.getPassword())
        .phoneNumber(user.getPhoneNumber())
        .profilePictureUrl(user.getProfilePictureUrl())
        .identityCardUrl(user.getIdentityCardUrl())
        .roles(rolesToRoleEntities(user.getRoles()))
        .address(addressToAddressEntity(user, user.getAddress()))
        .bankAccount(bankAccountToBankAccountEntity(user, user.getBankAccount()))
        .build();

    // userEntity.getBankAccount().setUser(userEntity);
    // userEntity.getAddress().setUser(userEntity);
    // userEntity.getBankAccount().setId(userEntity.getId());
    // userEntity.getAddress().setId(userEntity.getId());
    userEntity.getRoles().forEach(role -> role.setUser(userEntity));

    return userEntity;
  }

  public Admin adminEntityToAdmin(AdminEntity adminEntity) {
    return Admin.builder()
        .id(new UserId(adminEntity.getId()))
        .email(adminEntity.getEmail())
        .name(adminEntity.getName())
        .password(adminEntity.getPassword())
        .phoneNumber(adminEntity.getPhoneNumber())
        .role(adminEntity.getRole())
        .build();
  }

  public AdminEntity adminToAdminEntity(Admin admin) {
    return AdminEntity.builder()
        .id(admin.getId().getValue())
        .email(admin.getEmail())
        .name(admin.getName())
        .password(admin.getPassword())
        .phoneNumber(admin.getPhoneNumber())
        .role(admin.getRole())
        .build();
  }

  private BankAccountEntity bankAccountToBankAccountEntity(User user, BankAccount bankAccount) {
    if (bankAccount == null) {
      return null;
    }

    return BankAccountEntity.builder()
        .id(user.getId().getValue())
        .bank(bankAccount.getBank())
        .accountNumber(bankAccount.getAccountNumber())
        .accountHolderName(bankAccount.getAccountHolderName())
        .build();
  }

  private AddressEntity addressToAddressEntity(User user, Address address) {
    if (address == null) {
      return null;
    }

    return AddressEntity.builder()
        .id(user.getId().getValue())
        .city(address.getCity())
        .street(address.getStreet())
        .postalCode(address.getPostalCode())
        .build();
  }

  private List<RoleEntity> rolesToRoleEntities(List<UserRole> roles) {
    return roles.stream().map(role -> RoleEntity.builder().role(role).build())
        .collect(Collectors.toList());
  }

  private List<UserRole> roleEntitiesToRoles(List<RoleEntity> roles) {
    return roles.stream().map(role -> role.getRole()).collect(Collectors.toList());
  }

  private Address addressEntityToAddress(AddressEntity address) {
    if (address == null) {
      return null;
    }
    // return new Address(address.getId(), address.getStreet(), address.getCity(),
    // address.getPostalCode());
    return new Address(address.getStreet(), address.getCity(), address.getPostalCode());
  }

  private BankAccount bankAccountEntityToBankAccount(BankAccountEntity account) {
    // return new BankAccount(account.getId(), account.getNumber(),
    // account.getBank());
    if (account == null) {
      return null;
    }
    return new BankAccount(account.getBank(), account.getAccountNumber(), account.getAccountHolderName());
  }

}
