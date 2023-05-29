package com.temantani.project.service.domain.dto.message.land;

import java.util.UUID;

import com.temantani.domain.valueobject.Address;
import com.temantani.project.service.domain.valueobject.LandAvailabilityStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LandRegisteredMessage {
  private UUID landId;
  private UUID ownerId;
  private Address address;
  private LandAvailabilityStatus landStatus;
}
