package com.temantani.land.service.dataaccess.land.mapper;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.Address;
import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.dataaccess.land.entity.AddressEntity;
import com.temantani.land.service.dataaccess.land.entity.ApproverEntity;
import com.temantani.land.service.dataaccess.land.entity.AssessmentEntity;
import com.temantani.land.service.dataaccess.land.entity.LandEntity;
import com.temantani.land.service.dataaccess.land.entity.ProposalEntity;
import com.temantani.land.service.domain.entity.Approver;
import com.temantani.land.service.domain.entity.Land;
import com.temantani.land.service.domain.exception.LandDomainException;
import com.temantani.land.service.domain.valueobject.Area;
import com.temantani.land.service.domain.valueobject.Assessment;
import com.temantani.land.service.domain.valueobject.Height;
import com.temantani.land.service.domain.valueobject.Proposal;
import com.temantani.land.service.domain.valueobject.Area.AreaUnits;
import com.temantani.land.service.domain.valueobject.Height.HeightUnit;

@Component
public class LandDataAccessMapper {

  public LandEntity landToLandEntity(Land land) {
    return LandEntity.builder()
        .id(land.getId().getValue())
        .ownerId(land.getOwnerId().getValue())
        .areaValue(land.getArea().getValueInHectare())
        .areaUnit(land.getArea().getUnit().name())
        .certificateUrl(land.getCertificateUrl())
        .photos(land.getPhotos() != null ? String.join(Land.PHOTO_DELIMITER, land.getPhotos())
            : "")
        .landStatus(land.getLandStatus())
        .approver(land.getApprover() != null
            ? ApproverEntity.builder().id(land.getApprover().getId().getValue()).build()
            : null)
        .proposal(ProposalEntity.builder()
            .id(land.getId().getValue())
            .approvedAt(land.getProposal().getApprovedAt())
            .proposedAt(land.getProposal().getProposedAt())
            .revisionMessages(land.getProposal().getRevisionMessages() != null
                ? String.join(Proposal.MESSAGE_DELIMITER,
                    land.getProposal()
                        .getRevisionMessages())
                : "")
            .failureMessages(land.getProposal().getFailureMessages() != null
                ? String.join(Proposal.MESSAGE_DELIMITER,
                    land.getProposal().getFailureMessages())
                : "")
            .build())
        .assessment(land.getAssessment() != null ? AssessmentEntity.builder()
            .id(land.getId().getValue())
            .harvestSuitabilities(
                land.getAssessment().getHarvestSuitabilities() != null
                    ? String.join(Assessment.HARVEST_SUITABILITIES_DELIMITER,
                        land.getAssessment()
                            .getHarvestSuitabilities())
                    : "")
            .heightUnit(land.getAssessment().getGroundHeight().getUnit().name())
            .heightValue(land.getAssessment().getGroundHeight().getValue())
            .soilPh(land.getAssessment().getSoilPh())
            .landUsageHistory(land.getAssessment().getLandUsageHistory())
            .waterAvailabilityStatus(
                land.getAssessment().getWaterAvailabilityStatus())
            .build() : null)
        .address(AddressEntity.builder()
            .id(land.getId().getValue())
            .street(land.getAddress().getStreet())
            .city(land.getAddress().getCity())
            .postalCode(land.getAddress().getPostalCode())
            .build())
        .build();
  }

  public Land landEntityToLand(LandEntity entity) {
    return Land.builder()
        .landId(new LandId(entity.getId()))
        .ownerId(new UserId(entity.getOwnerId()))
        .proposal(Proposal.builder()
            .approvedAt(entity.getProposal().getApprovedAt())
            .proposedAt(entity.getProposal().getProposedAt())
            .revisionMessages(
                Arrays.asList(
                    entity.getProposal()
                        .getRevisionMessages()
                        .split(Proposal.MESSAGE_DELIMITER)))
            .failureMessages(Arrays
                .asList(entity.getProposal().getFailureMessages()
                    .split(Proposal.MESSAGE_DELIMITER)))
            .build())
        .approver(entity.getApprover() != null ? Approver.builder()
            .approverId(new UserId(entity.getApprover().getId()))
            .name(entity.getApprover().getName())
            .email(entity.getApprover().getEmail())
            .build() : null)
        .assessment(getAssesment(entity))
        .area(getArea(entity))
        .address(new Address(entity.getAddress().getStreet(), entity.getAddress().getCity(),
            entity.getAddress().getPostalCode()))
        .landStatus(entity.getLandStatus())
        .certificateUrl(entity.getCertificateUrl())
        .photos(Arrays.asList(entity.getPhotos().split(Land.PHOTO_DELIMITER)))
        .build();
  }

  private Area getArea(LandEntity entity) {
    try {
      return new Area(entity.getAreaValue(), AreaUnits.valueOf(entity.getAreaUnit()));
    } catch (Exception e) {
      throw new LandDomainException(e.getMessage(), e);
    }
  }

  private Assessment getAssesment(LandEntity entity) {
    if (entity.getAssessment() == null) {
      return null;
    }

    try {
      return Assessment.builder()
          .harvestSuitabilities(Arrays.asList(
              entity.getAssessment().getHarvestSuitabilities()
                  .split(Assessment.HARVEST_SUITABILITIES_DELIMITER)))
          .groundHeight(new Height(entity.getAssessment().getHeightValue(),
              HeightUnit.valueOf(entity.getAssessment().getHeightUnit())))
          .soilPh(entity.getAssessment().getSoilPh())
          .waterAvailabilityStatus(entity.getAssessment().getWaterAvailabilityStatus())
          .landUsageHistory(entity.getAssessment().getLandUsageHistory())
          .build();
    } catch (Exception e) {
      throw new LandDomainException(e.getMessage(), e);
    }
  }

}
