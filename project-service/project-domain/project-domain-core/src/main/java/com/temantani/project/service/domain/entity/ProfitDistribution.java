package com.temantani.project.service.domain.entity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import com.temantani.domain.entity.AggregateRoot;
import com.temantani.domain.helper.Helper;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.domain.exception.ProjectDomainException;
import com.temantani.project.service.domain.valueobject.DistributionStatus;
import com.temantani.project.service.domain.valueobject.ProfitDistributionDetailId;
import com.temantani.project.service.domain.valueobject.ProfitDistributionId;

public class ProfitDistribution extends AggregateRoot<ProfitDistributionId> {

  private final UserId managerId;
  private final ProjectId projectId;
  private final Money projectProfit;
  private final List<ProfitDistributionDetail> details;

  private ZonedDateTime distributedAt;
  private DistributionStatus status;

  public void transferProfit(UserId managerId, Map<ProfitDistributionDetailId, String> transfersProofs) {
    if (this.managerId.equals(managerId) == false) {
      throw new ProjectDomainException("Only manager: " + this.managerId.getValue() + " can transfer the profit");
    }

    for (ProfitDistributionDetail detail : details) {
      String proof = transfersProofs.get(detail.getId());
      if (proof == null || proof.isEmpty()) {
        throw new ProjectDomainException("Transfer proof for detail: " + detail.getId() + " is empty");
      }
      detail.completeTransferProof(proof);
    }

    status = DistributionStatus.COMPLETED;
    distributedAt = Helper.now();
  }

  public UserId getManagerId() {
    return managerId;
  }

  public ProjectId getProjectId() {
    return projectId;
  }

  public Money getProjectProfit() {
    return projectProfit;
  }

  public List<ProfitDistributionDetail> getDetails() {
    return details;
  }

  public ZonedDateTime getDistributedAt() {
    return distributedAt;
  }

  public DistributionStatus getStatus() {
    return status;
  }

  public static Builder builder() {
    return new Builder();
  }

  private ProfitDistribution(Builder builder) {
    super.setId(builder.id);
    this.managerId = builder.managerId;
    this.projectId = builder.projectId;
    this.projectProfit = builder.projectProfit;
    this.details = builder.details;
    this.distributedAt = builder.distributedAt;
    this.status = builder.status;
  }

  // Builder for ProfitDistributionBatch
  public static class Builder {
    private UserId managerId;
    private Money projectProfit;
    private ProfitDistributionId id;
    private ProjectId projectId;
    private List<ProfitDistributionDetail> details;
    private ZonedDateTime distributedAt;
    private DistributionStatus status;

    public Builder status(DistributionStatus status) {
      this.status = status;
      return this;
    }

    public Builder id(ProfitDistributionId id) {
      this.id = id;
      return this;
    }

    public Builder managerId(UserId managerId) {
      this.managerId = managerId;
      return this;
    }

    public Builder projectId(ProjectId projectId) {
      this.projectId = projectId;
      return this;
    }

    public Builder projectProfit(Money profit) {
      this.projectProfit = profit;
      return this;
    }

    public Builder details(List<ProfitDistributionDetail> details) {
      this.details = details;
      return this;
    }

    public Builder distributedAt(ZonedDateTime distributedAt) {
      this.distributedAt = distributedAt;
      return this;
    }

    public ProfitDistribution build() {
      return new ProfitDistribution(this);
    }
  }

}
