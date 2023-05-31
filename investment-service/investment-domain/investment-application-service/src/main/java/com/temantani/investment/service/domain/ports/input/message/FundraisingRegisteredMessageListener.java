package com.temantani.investment.service.domain.ports.input.message;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.temantani.domain.valueobject.Money;

public interface FundraisingRegisteredMessageListener {
  void registerFundraisingProject(UUID projectId, Money fundraisingTarget, ZonedDateTime fundraisingDeadline);
}
