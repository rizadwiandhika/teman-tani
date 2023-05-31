package com.temantani.investment.service.domain.event;

import java.time.ZonedDateTime;

import com.temantani.investment.service.domain.entity.Investment;

public class InvestmentCreatedEvent extends InvesmentEvent {

  public InvestmentCreatedEvent(Investment investment, ZonedDateTime createdAt) {
    super(investment, createdAt);
  }

}
