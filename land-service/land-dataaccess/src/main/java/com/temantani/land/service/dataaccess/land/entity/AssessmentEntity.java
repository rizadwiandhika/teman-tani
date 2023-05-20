package com.temantani.land.service.dataaccess.land.entity;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.temantani.land.service.domain.valueobject.WaterAvailabilityStatus;

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
@Table(name = "assessments")
public class AssessmentEntity {

  @Id
  private UUID id;

  private String harvestSuitabilities;
  private BigDecimal heightValue;
  private String heightUnit;
  private BigDecimal soilPh;
  private String landUsageHistory;

  @Enumerated(EnumType.STRING)
  private WaterAvailabilityStatus waterAvailabilityStatus;

}
