package com.temantani.land.service.domain.dto.query;

import java.util.List;

import com.temantani.land.service.domain.valueobject.LandStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LandDetailsData {
  private String id;
  private ProposalData proposal;
  private ApproverData approver;
  private AssesmentData assesment;
  private LandStatus landStatus;
  private AreaData area;
  private AddressData address;
  private String ceritifactionUrl;
  private List<String> photos;
}
