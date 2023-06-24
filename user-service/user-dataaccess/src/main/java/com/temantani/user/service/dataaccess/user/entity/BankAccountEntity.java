package com.temantani.user.service.dataaccess.user.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "bank_accounts")
public class BankAccountEntity {

  @Id
  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID id;

  // @OneToOne(cascade = CascadeType.ALL)
  // @JoinColumn(name = "USER_ID")
  // private UserEntity user;

  private String bank;
  private String accountNumber;
  private String accountHolderName;

}
