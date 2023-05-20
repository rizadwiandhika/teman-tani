package com.temantani.investment.service.domain.event;

import java.time.ZonedDateTime;

import com.temantani.domain.event.DomainEvent;
import com.temantani.investment.service.domain.entity.Investment;

public class InvesmentEvent implements DomainEvent<Investment> {

  private final Investment investment;
  private final ZonedDateTime createdAt;

  public Investment getInvestment() {
    return investment;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public InvesmentEvent(Investment investment, ZonedDateTime createdAt) {
    this.investment = investment;
    this.createdAt = createdAt;
  }

}
