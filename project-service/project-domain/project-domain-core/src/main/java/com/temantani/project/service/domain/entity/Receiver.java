package com.temantani.project.service.domain.entity;

import com.temantani.domain.entity.AggregateRoot;
import com.temantani.domain.valueobject.BankAccount;
import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.domain.exception.ProjectDomainException;
import com.temantani.project.service.domain.exception.SameBankAccountException;

public class Receiver extends AggregateRoot<UserId> {
  private BankAccount bankAccount;

  public Receiver(UserId receiverId, BankAccount bankAccount) {
    super.setId(receiverId);
    this.bankAccount = bankAccount;
  }

  public Receiver(UserId receiverId, Integer version, BankAccount bankAccount) {
    super.setId(receiverId);
    super.setVersion(version);
    this.bankAccount = bankAccount;
  }

  public void changeBankAccount(BankAccount bankAccount) throws ProjectDomainException {
    if (this.bankAccount.equals(bankAccount)) {
      throw new SameBankAccountException("Bank account is the same the old one");
    }

    this.bankAccount = bankAccount;
  }

  public BankAccount getBankAccount() {
    return bankAccount;
  }

}
