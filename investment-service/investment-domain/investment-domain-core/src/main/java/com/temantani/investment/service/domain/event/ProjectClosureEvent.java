package com.temantani.investment.service.domain.event;

import java.time.ZonedDateTime;

import com.temantani.investment.service.domain.entity.Fundraising;
import com.temantani.investment.service.domain.valueobject.FundraisingStatus;

public class ProjectClosureEvent {

  private final FundraisingStatus status;
  private final Fundraising project;
  private final ZonedDateTime createdAt;

  public ProjectClosureEvent(FundraisingStatus status, Fundraising project, ZonedDateTime createdAt) {
    this.status = status;
    this.project = project;
    this.createdAt = createdAt;
  }

  public FundraisingStatus getStatus() {
    return status;
  }

  public Fundraising getProject() {
    return project;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

}
