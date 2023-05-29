package com.temantani.domain.valueobject;

import java.util.UUID;

public class ProjectId extends BaseId<UUID> {

  public ProjectId(UUID id) {
    super(id);
  }

  public static ProjectId fromString(String id) {
    return new ProjectId(UUID.fromString(id));
  }

  public static ProjectId generate() {
    return new ProjectId(UUID.randomUUID());
  }

}
