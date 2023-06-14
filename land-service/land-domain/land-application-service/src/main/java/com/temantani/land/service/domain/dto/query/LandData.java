package com.temantani.land.service.domain.dto.query;

import java.util.List;

import com.temantani.land.service.domain.valueobject.LandStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LandData {

  private String id;
  private LandStatus landStatus;
  private AreaData area;
  private AddressData address;
  private List<String> photos;

}
