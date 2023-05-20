package com.temantani.land.service.domain.event;

import java.time.ZonedDateTime;

import com.temantani.domain.event.DomainEvent;
import com.temantani.land.service.domain.entity.Land;

public class LandEvent implements DomainEvent<Land> {

  private final Land land;
  private final ZonedDateTime createdAt;

  public Land getLand() {
    return land;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public LandEvent(Land land, ZonedDateTime createdAt) {
    this.land = land;
    this.createdAt = createdAt;
  }

}
