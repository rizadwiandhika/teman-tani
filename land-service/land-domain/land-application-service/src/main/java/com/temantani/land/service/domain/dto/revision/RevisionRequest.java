package com.temantani.land.service.domain.dto.revision;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.temantani.domain.valueobject.LandId;
import com.temantani.land.service.domain.dto.common.Address;
import com.temantani.land.service.domain.dto.common.Area;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RevisionRequest {

  @Valid
  @NotNull
  private Address address;

  @Valid
  @NotNull
  private Area area;

  private LandId landId;

  private String photoUrl;

  private String certificateUrl;
}
