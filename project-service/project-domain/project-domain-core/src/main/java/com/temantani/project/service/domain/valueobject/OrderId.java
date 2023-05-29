package com.temantani.project.service.domain.valueobject;

import java.util.UUID;

import com.temantani.domain.valueobject.BaseId;

public class OrderId extends BaseId<UUID> {

  public OrderId(UUID id) {
    super(id);
  }

  public static OrderId generate() {
    return new OrderId(UUID.randomUUID());
  }

  public static OrderId fromString(String id) {
    return new OrderId(UUID.fromString(id));
  }

}
