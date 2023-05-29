package com.temantani.investment.service.domain.entity;

import java.time.ZonedDateTime;

import com.temantani.domain.entity.AggregateRoot;
import com.temantani.domain.helper.Helper;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.investment.service.domain.exception.InvestmentDomainException;
import com.temantani.investment.service.domain.valueobject.ProjectStatus;

public class Project extends AggregateRoot<ProjectId> {
  private ProjectStatus status;
  private Money fundraisingTarget;
  private Money bookedFunds;

  private Money collectedFunds;
  private String description;
  private ZonedDateTime tenorDeadline;
  private ZonedDateTime createdAt;

  public Investment createInvestment(Investment investment) {
    if (getStatus() == ProjectStatus.CLOSING || getStatus() == ProjectStatus.CLOSED) {
      throw new InvestmentDomainException("Project is not open for new investment");
    }

    if (bookedFunds.isGreaterThan(getFundraisingTarget())) {
      throw new InvestmentDomainException("Project has already reached its fundraising target");
    }

    if (bookedFunds.add(investment.getAmount()).isGreaterThan(getFundraisingTarget())) {
      throw new InvestmentDomainException("Investment amount is too large, exceeding the fundraising target");
    }

    if (tenorDeadline.isBefore(Helper.now())) {
      throw new InvestmentDomainException("Project has already reached its fundraising end date");
    }

    investment.validateInvestment();
    investment.initializeInvestment();
    return investment;
  }

  public void acceptInvestment(Money amount) {
    if (status == ProjectStatus.CLOSED) {
      throw new InvestmentDomainException("Cannot add new investment since the project is CLOSED");
    }

    collectedFunds = collectedFunds.add(amount);
  }

  public void cancelInvestment(Money amount) {
    if (status == ProjectStatus.CLOSED) {
      throw new InvestmentDomainException("Cannot add new investment since the project is CLOSED");
    }

    bookedFunds = bookedFunds.substract(amount);
  }

  public Money getBookedFunds() {
    return bookedFunds;
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

  // Builder pattern
  private Project(Builder builder) {
    super.setId(builder.id);
    super.setVersion(builder.version);

    this.status = builder.status;
    this.fundraisingTarget = builder.fundraisingTarget;
    this.bookedFunds = builder.bookedFunds;
    this.collectedFunds = builder.collectedFunds;
    this.description = builder.description;
    this.tenorDeadline = builder.tenorDeadline;
    this.createdAt = builder.createdAt;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Money bookedFunds;
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

    public Builder bookedFunds(Money bookedFunds) {
      this.bookedFunds = bookedFunds;
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
