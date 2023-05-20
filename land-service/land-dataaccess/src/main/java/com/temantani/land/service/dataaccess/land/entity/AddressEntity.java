package com.temantani.land.service.dataaccess.land.entity;

import java.util.UUID;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.Table;

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
  private UUID id;
  private String street;
  private String city;
  private String postalCode;

}
