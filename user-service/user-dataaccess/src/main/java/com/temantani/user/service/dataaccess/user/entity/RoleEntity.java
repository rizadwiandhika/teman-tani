package com.temantani.user.service.dataaccess.user.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.temantani.domain.valueobject.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(RoleEntityId.class)
@Entity
@Table(name = "user_roles")
public class RoleEntity {
  @Id
  @ManyToOne
  @JoinColumn(name = "USER_ID")
  @Type(type = "org.hibernate.type.UUIDCharType")
  private UserEntity user;

  @Id
  @Enumerated(EnumType.STRING)
  private UserRole role;

}
