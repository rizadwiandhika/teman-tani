package com.temantani.project.service.domain.entity;

import com.temantani.domain.entity.AggregateRoot;
import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.domain.valueobject.UserId;

public class Investment extends AggregateRoot<InvestmentId> {

  private final ProjectId projectId;
  private final UserId investorId;
  private final Money amount;

  private Investment(InvestmentId id, ProjectId projectId, UserId investorId, Money amount) {
    super.setId(id);
    this.projectId = projectId;
    this.investorId = investorId;
    this.amount = amount;
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

  public static Builder builder() {
    return new Builder();
  }

  // Builder of Investment
  public static class Builder {
    private InvestmentId id;
    private ProjectId projectId;
    private UserId investorId;
    private Money amount;

    public Builder() {
    }

    public Builder id(InvestmentId id) {
      this.id = id;
      return this;
    }

    public Builder projectId(ProjectId id) {
      this.projectId = id;
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

    public Investment build() {
      return new Investment(id, projectId, investorId, amount);
    }
  }

}
