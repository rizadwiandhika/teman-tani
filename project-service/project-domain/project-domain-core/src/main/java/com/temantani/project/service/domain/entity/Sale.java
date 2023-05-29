package com.temantani.project.service.domain.entity;

import java.time.ZonedDateTime;

import com.temantani.domain.entity.AggregateRoot;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.project.service.domain.valueobject.OrderId;

public class Sale extends AggregateRoot<OrderId> {

  private final ProjectId projectId;
  private final Money amount;
  private final ZonedDateTime createdAt;

  public ProjectId getProjectId() {
    return projectId;
  }

  public Money getAmount() {
    return amount;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  private Sale(Builder builder) {
    super.setId(builder.id);
    this.projectId = builder.projectId;
    this.amount = builder.amount;
    this.createdAt = builder.createdAt;
  }

  // Builder for Sale
  public static class Builder {
    private OrderId id;
    private ProjectId projectId;
    private Money amount;
    private ZonedDateTime createdAt;

    public Builder() {
    }

    public Builder id(OrderId id) {
      this.id = id;
      return this;
    }

    public Builder projectId(ProjectId projectId) {
      this.projectId = projectId;
      return this;
    }

    public Builder amount(Money amount) {
      this.amount = amount;
      return this;
    }

    public Builder createdAt(ZonedDateTime createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Sale build() {
      return new Sale(this);
    }
  }

}
