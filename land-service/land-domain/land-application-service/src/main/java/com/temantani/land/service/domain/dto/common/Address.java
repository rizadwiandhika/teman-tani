package com.temantani.land.service.domain.dto.common;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {

  @NotNull
  private String street;

  @NotNull
  private String city;

  @NotNull
  private String postalCode;

}
