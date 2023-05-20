package com.temantani.user.service.dataaccess.user.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.temantani.domain.valueobject.AdminRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "admins")
public class AdminEntity {

  @Id
  private UUID id;
  private String name;
  private String email;
  private String password;
  private String phoneNumber;

  @Enumerated(EnumType.STRING)
  private AdminRole role;

}
