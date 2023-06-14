package com.temantani.domain.valueobject;

import java.util.List;

import com.temantani.domain.exception.DomainException;

public class BankAccount {

  private static final List<String> supportedBanks = List.of("BCA", "BNI", "BRI", "MANDIRI");
  public static final BankAccount EMPTY = new BankAccount();

  private final String bank;
  private final String accountNumber;
  private final String accountHolderName;

  private BankAccount() {
    this.bank = "";
    this.accountNumber = "";
    this.accountHolderName = "";
  }

  public BankAccount(String bank, String accountNumber, String accountHolderName) {
    bank = bank.trim().toUpperCase();
    if (!supportedBanks.contains(bank)) {
      throw new DomainException("Bank is not supported");
    }

    this.bank = bank;
    this.accountNumber = accountNumber;
    this.accountHolderName = accountHolderName;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getBank() {
    return bank;
  }

  public String getAccountHolderName() {
    return accountHolderName;
  }

  public Boolean isValid() {
    if (bank == null || accountNumber == null || accountHolderName == null) {
      return false;
    }

    if (bank.trim().isEmpty() || accountNumber.trim().isEmpty() || accountHolderName.trim().isEmpty()) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((bank == null) ? 0 : bank.hashCode());
    result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
    result = prime * result + ((accountHolderName == null) ? 0 : accountHolderName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    BankAccount other = (BankAccount) obj;
    if (bank == null) {
      if (other.bank != null)
        return false;
    } else if (!bank.equals(other.bank))
      return false;
    if (accountNumber == null) {
      if (other.accountNumber != null)
        return false;
    } else if (!accountNumber.equals(other.accountNumber))
      return false;
    if (accountHolderName == null) {
      if (other.accountHolderName != null)
        return false;
    } else if (!accountHolderName.equals(other.accountHolderName))
      return false;
    return true;
  }

}
