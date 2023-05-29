package com.temantani.project.service.domain.event;

import java.time.ZonedDateTime;

import com.temantani.domain.event.DomainEvent;
import com.temantani.project.service.domain.entity.Project;

public class ProjectEvent implements DomainEvent<Project> {

  private final Project project;
  private final ZonedDateTime createdAt;

  public Project getProject() {
    return project;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public ProjectEvent(Project project, ZonedDateTime createdAt) {
    this.project = project;
    this.createdAt = createdAt;
  }

}
