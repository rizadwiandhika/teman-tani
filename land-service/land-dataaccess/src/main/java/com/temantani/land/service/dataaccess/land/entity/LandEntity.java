package com.temantani.land.service.dataaccess.land.entity;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.temantani.land.service.domain.valueobject.LandStatus;

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

  private UUID ownerId;
  private BigDecimal areaValue;
  private String areaUnit;
  private String certificateUrl;
  private String photos;

  @Enumerated(EnumType.STRING)
  private LandStatus landStatus;

  @ManyToOne
  @JoinColumn(name = "APPROVER_ID")
  private ApproverEntity approver;

  @OneToOne(optional = true, cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private ProposalEntity proposal;

  @OneToOne(optional = true, cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private AssessmentEntity assessment;

  @OneToOne(optional = true, cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private AddressEntity address;

}
