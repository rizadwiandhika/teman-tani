package com.temantani.land.service.domain.dto.proposal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.domain.dto.common.Address;
import com.temantani.land.service.domain.dto.common.Area;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProposalRequest {

  @NotNull
  @Valid
  private Address address;

  @NotNull
  @Valid
  private Area area;

  private String photoUrl;

  private String certificateUrl;

  private UserId borrowerId;
}
