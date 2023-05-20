package com.temantani.investment.service.domain.entity;

import java.time.ZonedDateTime;

import com.temantani.domain.entity.AggregateRoot;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.investment.service.domain.valueobject.ProjectStatus;

public class Project extends AggregateRoot<ProjectId> {
  private ProjectStatus status;
  private Money fundraisingTarget;
  private Money collectedFunds;
  private String description;
  private ZonedDateTime tenorDeadline;

  private ZonedDateTime createdAt;
  private Integer version;

  public void addInvestment(Money amount) {
    collectedFunds = collectedFunds.add(amount);
  }

  public ProjectStatus getStatus() {
    return status;
  }

  public Money getFundraisingTarget() {
    return fundraisingTarget;
  }

  public Money getCollectedFunds() {
    return collectedFunds;
  }

  public String getDescription() {
    return description;
  }

  public ZonedDateTime getTenorDeadline() {
    return tenorDeadline;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public Integer getVersion() {
    return version;
  }

  // Builder pattern
  private Project(Builder builder) {
    super.setId(builder.id);
    this.status = builder.status;
    this.fundraisingTarget = builder.fundraisingTarget;
    this.collectedFunds = builder.collectedFunds;
    this.description = builder.description;
    this.tenorDeadline = builder.tenorDeadline;
    this.createdAt = builder.createdAt;
    this.version = builder.version;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private ProjectId id;
    private ProjectStatus status;
    private Money fundraisingTarget;
    private Money collectedFunds;
    private String description;
    private ZonedDateTime tenorDeadline;
    private ZonedDateTime createdAt;
    private Integer version;

    public Builder id(ProjectId id) {
      this.id = id;
      return this;
    }

    public Builder status(ProjectStatus status) {
      this.status = status;
      return this;
    }

    public Builder fundraisingTarget(Money fundraisingTarget) {
      this.fundraisingTarget = fundraisingTarget;
      return this;
    }

    public Builder collectedFunds(Money collectedFunds) {
      this.collectedFunds = collectedFunds;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder tenorDeadline(ZonedDateTime tenorDeadline) {
      this.tenorDeadline = tenorDeadline;
      return this;
    }

    public Builder createdAt(ZonedDateTime createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder version(Integer version) {
      this.version = version;
      return this;
    }

    public Project build() {
      return new Project(this);
    }
  }
}
