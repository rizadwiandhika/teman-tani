package com.temantani.project.service.dataaccess.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.temantani.project.service.domain.valueobject.LandAvailabilityStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lands")
public class LandEntity {

  @Id
  private UUID id;

  @Enumerated(EnumType.STRING)
  private LandAvailabilityStatus availabilityStatus;

  @Version
  private Integer version;

  private UUID ownerId;
  private String street;
  private String city;
  private String postalCode;

}
