package com.temantani.project.service.domain.valueobject;

import java.util.UUID;

import com.temantani.domain.valueobject.BaseId;

public class ExpenseId extends BaseId<UUID> {

  public ExpenseId(UUID id) {
    super(id);
  }

  public static ExpenseId generate() {
    return new ExpenseId(UUID.randomUUID());
  }

  public static ExpenseId fromString(String id) {
    return new ExpenseId(UUID.fromString(id));
  }

}
