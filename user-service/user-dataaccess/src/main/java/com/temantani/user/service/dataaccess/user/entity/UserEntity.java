package com.temantani.user.service.dataaccess.user.entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID id;
  private String name;
  private String email;
  private String password;
  private String phoneNumber;
  private String profilePictureUrl;
  private String identityCardUrl;
  private String identityCardNumber;

  @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  private List<RoleEntity> roles;
  // @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch =
  // FetchType.EAGER)
  // private AddressEntity address;
  // @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch =
  // FetchType.EAGER)
  // private BankAccountEntity bankAccount;
  @OneToOne(cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private AddressEntity address;

  @OneToOne(optional = true, cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private BankAccountEntity bankAccount;

}
