package com.temantani.project.service.domain.entity;

import com.temantani.domain.entity.BaseEntity;
import com.temantani.domain.valueobject.BankAccount;
import com.temantani.domain.valueobject.Money;
import com.temantani.project.service.domain.valueobject.ProfitDistributionId;
import com.temantani.project.service.domain.valueobject.ProfitReceiver;
import com.temantani.project.service.domain.exception.ProjectDomainException;
import com.temantani.project.service.domain.valueobject.ProfitDistributionDetailId;

public class ProfitDistributionDetail extends BaseEntity<ProfitDistributionDetailId> {

  private final ProfitDistributionId profitDistributionId;
  private final ProfitReceiver receiver;
  private final BankAccount bankAccount;

  private final Money amount;
  private String transferProofUrl;

  public void completeTransferProof(String transferProofUrl) {
    if (transferProofUrl == null || transferProofUrl.isEmpty()) {
      throw new ProjectDomainException("Transfer proof url is empty");
    }
    this.transferProofUrl = transferProofUrl;
  }

  public static Builder builder() {
    return new Builder();
  }

  public ProfitDistributionId getProfitDistributionId() {
    return profitDistributionId;
  }

  public ProfitReceiver getReceiver() {
    return receiver;
  }

  public BankAccount getBankAccount() {
    return bankAccount;
  }

  public Money getAmount() {
    return amount;
  }

  public String getTransferProofUrl() {
    return transferProofUrl;
  }

  private ProfitDistributionDetail(Builder builder) {
    super.setId(builder.id);
    this.profitDistributionId = builder.profitDistributionId;
    this.receiver = builder.receiver;
    this.bankAccount = builder.bankAccount;
    this.amount = builder.amount;
    this.transferProofUrl = builder.transferProofUrl;
  }

  // Builder for ProfitDistributionDetail
  public static class Builder {
    private ProfitDistributionDetailId id;
    private ProfitDistributionId profitDistributionId;
    private ProfitReceiver receiver;
    private BankAccount bankAccount;
    private Money amount;
    private String transferProofUrl;

    public Builder id(ProfitDistributionDetailId id) {
      this.id = id;
      return this;
    }

    public Builder profitDistributionId(ProfitDistributionId profitDistributionId) {
      this.profitDistributionId = profitDistributionId;
      return this;
    }

    public Builder receiver(ProfitReceiver receiver) {
      this.receiver = receiver;
      return this;
    }

    public Builder bankAccount(BankAccount bankAccount) {
      this.bankAccount = bankAccount;
      return this;
    }

    public Builder amount(Money amount) {
      this.amount = amount;
      return this;
    }

    public Builder transferProofUrl(String transferProofUrl) {
      this.transferProofUrl = transferProofUrl;
      return this;
    }

    public ProfitDistributionDetail build() {
      return new ProfitDistributionDetail(this);
    }
  }

}
