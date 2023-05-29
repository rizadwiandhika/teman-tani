package com.temantani.project.service.domain.event;

import java.time.ZonedDateTime;

import com.temantani.project.service.domain.entity.Project;

public class ProjectStatusUpdatedEvent extends ProjectEvent {

  public ProjectStatusUpdatedEvent(Project project, ZonedDateTime createdAt) {
    super(project, createdAt);
  }

}
