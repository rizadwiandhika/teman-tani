package com.temantani.land.service.domain.event;

import java.time.ZonedDateTime;

import com.temantani.land.service.domain.entity.Land;

public class LandApprovedEvent extends LandEvent {

  public LandApprovedEvent(Land land, ZonedDateTime createdAt) {
    super(land, createdAt);
  }

}
