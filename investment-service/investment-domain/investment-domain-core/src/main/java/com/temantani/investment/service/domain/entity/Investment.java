package com.temantani.investment.service.domain.entity;

import static com.temantani.investment.service.domain.valueobject.InvestmentStatus.PENDING;

import java.util.List;
import java.util.UUID;

import com.temantani.domain.entity.AggregateRoot;
import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.investment.service.domain.exception.InvestmentDomainException;
import com.temantani.investment.service.domain.exception.InvestmentPaymentException;
import com.temantani.investment.service.domain.valueobject.InvestmentStatus;

public class Investment extends AggregateRoot<InvestmentId> {

  private final ProjectId projectId;
  private final UserId investorId;
  private final Money amount;
  private InvestmentStatus status;
  private List<String> failureReasons;

  public static final String FAILURE_REASONS_DELIMITER = ",";

  public void validateInvestment() {
    validateMandatoryFields();

    if (status != null) {
      throw new InvestmentDomainException("Investment is not in valid state for intialization");
    }

    if (amount.isGreaterThanZero() == false) {
      throw new InvestmentDomainException("Investment amount must be greater than zero");
    }

  }

  public void initializeInvestment() {
    super.setId(new InvestmentId(UUID.randomUUID()));
    status = PENDING;
  }

  public void pay() {
    validateMandatoryFields();

    if (status != PENDING) {
      throw new InvestmentPaymentException("Investment is not in valid state for payment: " + status.name());
    }

    status = InvestmentStatus.PAID;
  }

  public void cancel(List<String> reasons) {
    if (status != PENDING) {
      throw new InvestmentDomainException("Investment is not in valid state for cancellation: " + status.name());
    }

    status = InvestmentStatus.CANCELED;
    failureReasons = reasons;
  }

  private void validateMandatoryFields() {
    if (projectId == null || investorId == null || projectId.getValue().toString().isBlank()
        || investorId.getValue().toString().isBlank()) {
      throw new InvestmentDomainException("Investment must have a project and investor");
    }
  }

  // Constructor, builder, getters, setters
  private Investment(Builder builder) {
    super.setId(builder.id);
    super.setVersion(builder.version);
    this.projectId = builder.projectId;
    this.investorId = builder.investorId;
    this.amount = builder.amount;
    this.status = builder.status;
    this.failureReasons = builder.failureReasons;
  }

  public ProjectId getProjectId() {
    return projectId;
  }

  public UserId getInvestorId() {
    return investorId;
  }

  public Money getAmount() {
    return amount;
  }

  public InvestmentStatus getStatus() {
    return status;
  }

  public List<String> getFailureReasons() {
    return failureReasons;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private InvestmentId id;
    private Integer version;
    private ProjectId projectId;
    private UserId investorId;
    private Money amount;
    private InvestmentStatus status;
    private List<String> failureReasons;

    public Builder version(Integer version) {
      this.version = version;
      return this;
    }

    public Builder id(InvestmentId id) {
      this.id = id;
      return this;
    }

    public Builder projectId(ProjectId projectId) {
      this.projectId = projectId;
      return this;
    }

    public Builder investorId(UserId investorId) {
      this.investorId = investorId;
      return this;
    }

    public Builder amount(Money amount) {
      this.amount = amount;
      return this;
    }

    public Builder status(InvestmentStatus status) {
      this.status = status;
      return this;
    }

    public Builder failureReasons(List<String> failureReasons) {
      this.failureReasons = failureReasons;
      return this;
    }

    public Investment build() {
      return new Investment(this);
    }
  }

}
