package com.temantani.project.service.domain.valueobject;

import java.util.UUID;

import com.temantani.domain.valueobject.BaseId;

public class ProfitDistributionId extends BaseId<UUID> {

  public ProfitDistributionId(UUID id) {
    super(id);
  }

  public static ProfitDistributionId generate() {
    return new ProfitDistributionId(UUID.randomUUID());
  }

  public static ProfitDistributionId fromString(String id) {
    return new ProfitDistributionId(UUID.fromString(id));
  }

}
