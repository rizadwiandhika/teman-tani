package com.temantani.investment.service.domain.event;

import java.time.ZonedDateTime;

import com.temantani.investment.service.domain.entity.Project;
import com.temantani.investment.service.domain.valueobject.ProjectStatus;

public class ProjectClosureEvent {

  private final ProjectStatus status;
  private final Project project;
  private final ZonedDateTime createdAt;

  public ProjectClosureEvent(ProjectStatus status, Project project, ZonedDateTime createdAt) {
    this.status = status;
    this.project = project;
    this.createdAt = createdAt;
  }

  public ProjectStatus getStatus() {
    return status;
  }

  public Project getProject() {
    return project;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

}
