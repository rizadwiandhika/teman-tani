package com.temantani.investment.service.domain.entity;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.temantani.domain.entity.AggregateRoot;
import com.temantani.domain.helper.Helper;
import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.investment.service.domain.event.InvestmentCreatedEvent;
import com.temantani.investment.service.domain.event.ProjectClosureEvent;
import com.temantani.investment.service.domain.exception.FundraisingClosedException;
import com.temantani.investment.service.domain.exception.InvestmentDomainException;
import com.temantani.investment.service.domain.valueobject.InvestmentStatus;
import com.temantani.investment.service.domain.valueobject.FundraisingStatus;

public class Fundraising extends AggregateRoot<ProjectId> {
  private FundraisingStatus status;
  private Money fundraisingTarget;
  private Money bookedFunds;
  private Money collectedFunds;
  private String description;
  private ZonedDateTime tenorDeadline;
  private ZonedDateTime createdAt;
  private List<Investment> investments;

  public static Fundraising registerNewFundraising(ProjectId projectId, String description, Money fundraisingTarget,
      ZonedDateTime tenorDeadline) {
    if (projectId == null) {
      throw new InvestmentDomainException("Fundraising project should have an ID");
    }

    if (fundraisingTarget.isGreaterThanZero() == false) {
      throw new InvestmentDomainException("Fundraising target should be greater than zero");
    }

    if (tenorDeadline == null || tenorDeadline.isBefore(Helper.now())) {
      throw new InvestmentDomainException("Fundraising deadline should not be behind than present time");
    }

    return Fundraising.builder()
        .id(projectId)
        .description(description)
        .fundraisingTarget(fundraisingTarget)
        .tenorDeadline(tenorDeadline)
        .status(FundraisingStatus.OPEN)
        .bookedFunds(Money.ZERO)
        .collectedFunds(Money.ZERO)
        .build();
  }

  public InvestmentCreatedEvent createInvestment(UserId investorId, Money amount) {
    if (getStatus() == FundraisingStatus.CLOSING || getStatus() == FundraisingStatus.CLOSED) {
      throw new InvestmentDomainException("Project is not open for new investment");
    }

    if (amount.isGreaterThanZero() == false) {
      throw new InvestmentDomainException("Investment amount cannot be zero or less");
    }

    if (bookedFunds.isGreaterThan(fundraisingTarget)) {
      throw new InvestmentDomainException("Project has already reached its fundraising target");
    }

    if (bookedFunds.add(amount).isGreaterThan(fundraisingTarget)) {
      throw new InvestmentDomainException("Investment amount is too large, exceeding the fundraising target");
    }

    if (Helper.now().isAfter(tenorDeadline)) {
      throw new InvestmentDomainException("Project has already reached its fundraising end date");
    }

    Investment investment = Investment.builder()
        .id(new InvestmentId(UUID.randomUUID()))
        .projectId(getId())
        .investorId(investorId)
        .amount(amount)
        .expiredAt(Helper.now().plusMinutes(2))
        .status(InvestmentStatus.PENDING)
        .createdAt(Helper.now())
        .build();

    investments.add(investment);
    bookedFunds = bookedFunds.add(amount);

    return new InvestmentCreatedEvent(investment, Helper.now());
  }

  public void acceptInvestment(InvestmentId investmentId) {
    if (status == FundraisingStatus.CLOSED) {
      throw new InvestmentDomainException("Cannot add new investment since the project is CLOSED");
    }

    Investment investment = investments.stream().filter(i -> i.getId().equals(investmentId)).findFirst().orElse(null);
    if (investment == null) {
      throw new InvestmentDomainException(
          "Investment not found: " + investmentId.getValue() + " in project: " + getId().getValue());
    }

    investment.pay();
    collectedFunds = collectedFunds.add(investment.getAmount());
  }

  public void cancelInvestment(InvestmentId investmentId, List<String> reasons) {
    if (status == FundraisingStatus.CLOSED) {
      throw new InvestmentDomainException("Cannot add new investment since the project is CLOSED");
    }

    Investment investment = investments.stream().filter(i -> i.getId().equals(investmentId)).findFirst().orElse(null);
    if (investment == null) {
      throw new InvestmentDomainException(
          "Investment not found: " + investmentId.getValue() + " in project: " + getId().getValue());
    }

    investment.cancel(reasons);
    bookedFunds = bookedFunds.substract(investment.getAmount());
  }

  public void cancelAllExipiredInvestments() {
    if (status == FundraisingStatus.CLOSED) {
      throw new FundraisingClosedException("Cannot close project fundraising since it is already CLOSED");
    }

    investments.stream().forEach(i -> {
      if (i.isExpired()) {
        i.cancel(List.of("Investment has been expired"));
        bookedFunds = bookedFunds.substract(i.getAmount());
      }
    });
  }

  public ProjectClosureEvent close() throws FundraisingClosedException {
    if (status == FundraisingStatus.CLOSED) {
      throw new FundraisingClosedException("Cannot close project fundraising since it is already CLOSED");
    }

    List<Investment> pendingInvestments = investments.stream().filter(i -> i.getStatus() == InvestmentStatus.PENDING)
        .collect(Collectors.toList());

    status = pendingInvestments.isEmpty() ? FundraisingStatus.CLOSED : FundraisingStatus.CLOSING;
    return new ProjectClosureEvent(status, this, createdAt);
  }

  public Money getBookedFunds() {
    return bookedFunds;
  }

  public FundraisingStatus getStatus() {
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

  public List<Investment> getInvestments() {
    return investments;
  }

  // Builder pattern
  private Fundraising(Builder builder) {
    super.setId(builder.id);
    super.setVersion(builder.version);

    this.investments = builder.investments == null ? new ArrayList<>()
        : builder.investments.stream().collect(Collectors.toList());
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
    private FundraisingStatus status;
    private Money fundraisingTarget;
    private Money collectedFunds;
    private String description;
    private ZonedDateTime tenorDeadline;
    private ZonedDateTime createdAt;
    private Integer version;
    private List<Investment> investments;

    public Builder investments(List<Investment> investments) {
      this.investments = investments;
      return this;
    }

    public Builder id(ProjectId id) {
      this.id = id;
      return this;
    }

    public Builder bookedFunds(Money bookedFunds) {
      this.bookedFunds = bookedFunds;
      return this;
    }

    public Builder status(FundraisingStatus status) {
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

    public Fundraising build() {
      return new Fundraising(this);
    }
  }
}
