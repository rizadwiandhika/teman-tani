package com.temantani.land.service.domain.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.Address;
import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.domain.dto.approval.ApprovalRequest;
import com.temantani.land.service.domain.dto.message.CreateApproverMessage;
import com.temantani.land.service.domain.dto.message.CreateBorrowerMessage;
// import com.temantani.land.service.domain.dto.common.Address;
import com.temantani.land.service.domain.dto.proposal.ProposalRequest;
import com.temantani.land.service.domain.dto.query.AddressData;
import com.temantani.land.service.domain.dto.query.ApproverData;
import com.temantani.land.service.domain.dto.query.AreaData;
import com.temantani.land.service.domain.dto.query.AssesmentData;
import com.temantani.land.service.domain.dto.query.HeightData;
import com.temantani.land.service.domain.dto.query.LandData;
import com.temantani.land.service.domain.dto.query.LandDetailsData;
import com.temantani.land.service.domain.dto.query.ProposalData;
import com.temantani.land.service.domain.entity.Approver;
import com.temantani.land.service.domain.entity.Borrower;
import com.temantani.land.service.domain.entity.Land;
import com.temantani.land.service.domain.event.LandApprovedEvent;
import com.temantani.land.service.domain.exception.LandDomainException;
import com.temantani.land.service.domain.outbox.model.LandRegisteredEventPayload;
import com.temantani.land.service.domain.valueobject.Area;
import com.temantani.land.service.domain.valueobject.Assessment;
import com.temantani.land.service.domain.valueobject.Height;
import com.temantani.land.service.domain.valueobject.WaterAvailabilityStatus;
import com.temantani.land.service.domain.valueobject.Area.AreaUnits;
import com.temantani.land.service.domain.valueobject.Height.HeightUnit;

@Component
public class LandDataMapper {

  public Land proposalRequestToLand(ProposalRequest request) {
    AreaUnits areaUnit;
    try {
      areaUnit = AreaUnits.valueOf(request.getArea().getUnit());
    } catch (Exception e) {
      throw new LandDomainException(e.getMessage(), e);
    }

    return Land.builder()
        .address(new Address(request.getAddress().getStreet(), request.getAddress().getCity(),
            request.getAddress().getPostalCode()))
        .area(new Area(request.getArea().getValue(), areaUnit))
        .photos(new ArrayList<>(Arrays.asList(request.getPhotoUrl())))
        .certificateUrl(request.getCertificateUrl())
        .build();
  }

  public Address adrressDtoToAddress(com.temantani.land.service.domain.dto.common.Address addressDTO) {
    return new Address(addressDTO.getStreet(), addressDTO.getCity(), addressDTO.getPostalCode());
  }

  public Area areaDtoToArea(com.temantani.land.service.domain.dto.common.Area areaDTO) {
    return new Area(areaDTO.getValue(), AreaUnits.valueOf(areaDTO.getUnit()));
  }

  public Assessment approvalRequestToAssesment(ApprovalRequest request) {
    HeightUnit heightUnit;
    WaterAvailabilityStatus waterAvailabilityStatus;
    try {
      heightUnit = HeightUnit.valueOf(request.getGroundHeightUnit());
      waterAvailabilityStatus = WaterAvailabilityStatus.valueOf(request.getWaterAvailabilityStatus());
    } catch (Exception e) {
      throw new LandDomainException("Invalid value: " + request.getGroundHeightUnit(), e);
    }

    return Assessment.builder()
        .harvestSuitabilities(request.getHarvestSuitabilities())
        .groundHeight(new Height(request.getGroundHeightValue(), heightUnit))
        .soilPh(request.getSoilPh())
        .waterAvailabilityStatus(waterAvailabilityStatus)
        .landUsageHistory(request.getLandUsageHistory())
        .build();
  }

  public Approver createApproverMessageToApprover(CreateApproverMessage message) {
    return Approver.builder()
        .approverId(new UserId(UUID.fromString(message.getApproverId())))
        .name(message.getName())
        .email(message.getEmail())
        .build();
  }

  public Borrower createBorrowerMessageToBorrower(CreateBorrowerMessage message) {
    return Borrower.builder()
        .id(new UserId(UUID.fromString(message.getBorrowerId())))
        .email(message.getEmail())
        .name(message.getName())
        .profilePictureUrl(message.getProfilePictureUrl())
        .build();
  }

  public LandRegisteredEventPayload landApprovedEventToLandApprovedEventMessage(LandApprovedEvent domainEvent) {
    Land land = domainEvent.getLand();
    return LandRegisteredEventPayload.builder()
        .landId(land.getId().getValue())
        .ownerId(land.getOwnerId().getValue())
        .approverId(land.getApprover().getId().getValue())
        .approvedAt(land.getProposal().getApprovedAt())
        .proposedAt(land.getProposal().getProposedAt())
        .harvestSuitabilities(
            String.join(Assessment.HARVEST_SUITABILITIES_DELIMITER, land.getAssessment().getHarvestSuitabilities()))
        .groundHeightValue(land.getAssessment().getGroundHeight().getValue())
        .groundHeightUnit(land.getAssessment().getGroundHeight().getUnit().name())
        .soilPh(land.getAssessment().getSoilPh())
        .waterAvailabilityStatus(land.getAssessment().getWaterAvailabilityStatus().name())
        .landUsageHistory(land.getAssessment().getLandUsageHistory())
        .landStatus(land.getLandStatus().name())
        .areaValue(land.getArea().getValueInHectare())
        .areaUnit(land.getArea().getUnit().name())
        .street(land.getAddress().getStreet())
        .postalCode(land.getAddress().getPostalCode())
        .city(land.getAddress().getCity())
        .certificateUrl(land.getCertificateUrl())
        .photos(String.join(",", land.getPhotos()))
        .build();
  }

  public LandData landToLandData(Land land) {
    return LandData.builder()
        .id(land.getId().getValue().toString())
        .landStatus(land.getLandStatus())
        .area(AreaData.builder()
            .unit(land.getArea().getUnit().name())
            .valueInHectare(land.getArea().getValueInHectare())
            .build())
        .address(AddressData.builder()
            .street(land.getAddress().getStreet())
            .city(land.getAddress().getCity())
            .postalCode(land.getAddress().getPostalCode())
            .build())
        .photos(land.getPhotos())
        .build();
  }

  public LandDetailsData landToLandDetailsData(Land land) {
    return LandDetailsData.builder()
        .id(land.getId().getValue().toString())
        .proposal(land.getProposal() == null ? null
            : ProposalData.builder()
                .approvedAt(land.getProposal().getApprovedAt())
                .proposedAt(land.getProposal().getProposedAt())
                .reivisionMessages(land.getProposal().getRevisionMessages())
                .failureMessages(land.getProposal().getFailureMessages())
                .build())
        .approver(land.getApprover() == null ? null
            : ApproverData.builder()
                .id(land.getApprover().getId().getValue().toString())
                .email(land.getApprover().getEmail())
                .name(land.getApprover().getName())
                .build())
        .assesment(land.getAssessment() == null ? null
            : AssesmentData.builder()
                .harvestSuitabilities(land.getAssessment().getHarvestSuitabilities())
                .groundHeight(HeightData.builder().value(land.getAssessment().getGroundHeight().getValue())
                    .unit(land.getAssessment().getGroundHeight().getUnit().name()).build())
                .soilPh(land.getAssessment().getSoilPh())
                .waterAvailabilityStatus(land.getAssessment().getWaterAvailabilityStatus().name())
                .landUsageHistory(land.getAssessment().getLandUsageHistory())
                .build())
        .landStatus(land.getLandStatus())
        .area(land.getArea() == null ? null
            : AreaData.builder()
                .unit(land.getArea().getUnit().name())
                .valueInHectare(land.getArea().getValueInHectare())
                .build())
        .address(land.getAddress() == null ? null
            : AddressData.builder()
                .street(land.getAddress().getStreet())
                .city(land.getAddress().getCity())
                .postalCode(land.getAddress().getPostalCode())
                .build())
        .ceritifactionUrl(land.getCertificateUrl())
        .photos(land.getPhotos())
        .build();
  }
}
