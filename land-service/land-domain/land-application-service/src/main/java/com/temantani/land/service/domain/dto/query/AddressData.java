package com.temantani.land.service.domain.dto.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddressData {
  private String street;
  private String city;
  private String postalCode;
}
