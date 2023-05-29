package com.temantani.project.service.domain.valueobject;

import java.util.UUID;

import com.temantani.domain.valueobject.BaseId;

public class ProfitDistributionDetailId extends BaseId<UUID> {

  public ProfitDistributionDetailId(UUID id) {
    super(id);
  }

  public static ProfitDistributionDetailId generate() {
    return new ProfitDistributionDetailId(UUID.randomUUID());
  }

  public static ProfitDistributionDetailId fromString(String id) {
    return new ProfitDistributionDetailId(UUID.fromString(id));
  }

}
