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
@Table(name = "addresses")
public class AddressEntity {
  @Id
  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID id;

  // @OneToOne(cascade = CascadeType.ALL)
  // @JoinColumn(name = "USER_ID")
  // private UserEntity user;

  private String street;
  private String city;
  private String postalCode;
}
