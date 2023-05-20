package com.temantani.land.service.domain.mapper;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.Address;
import com.temantani.land.service.domain.dto.approval.ApprovalRequest;
// import com.temantani.land.service.domain.dto.common.Address;
import com.temantani.land.service.domain.dto.proposal.ProposalRequest;
import com.temantani.land.service.domain.entity.Land;
import com.temantani.land.service.domain.exception.LandDomainException;
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
}
