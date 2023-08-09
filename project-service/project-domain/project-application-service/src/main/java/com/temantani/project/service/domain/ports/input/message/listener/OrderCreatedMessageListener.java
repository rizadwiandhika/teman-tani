package com.temantani.project.service.domain.ports.input.message.listener;

import java.util.UUID;

import com.temantani.domain.valueobject.Money;

public interface OrderCreatedMessageListener {

  void addIncomeFromOrder(UUID projectId, UUID orderId, Money amount);

}
