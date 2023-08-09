package com.temantani.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {
  private final BigDecimal amount;

  public static final Money ZERO = new Money(BigDecimal.ZERO);

  public Money(BigDecimal amount) {
    this.amount = amount;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public boolean isGreaterThanZero() {
    return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
  }

  public boolean isGreaterThan(Money money) {
    return amount != null && money != null && amount.compareTo(money.getAmount()) > 0;
  }

  public Money add(Money money) {
    return new Money(setScale(amount.add(money.getAmount())));
  }

  public Money substract(Money money) {
    return new Money(setScale(amount.subtract(money.getAmount())));
  }

  public Money multiply(int multiplier) {
    return new Money(setScale(amount.multiply(new BigDecimal(multiplier))));
  }

  public Money multiply(BigDecimal multiplier) {
    return new Money(setScale(amount.multiply(multiplier)));
  }

  public BigDecimal divide(Money divisor) {
    BigDecimal quotient = amount.divide(divisor.getAmount(), 2, RoundingMode.HALF_UP);
    return quotient.setScale(2);
  }

  private BigDecimal setScale(BigDecimal input) {
    return input.setScale(2, RoundingMode.HALF_UP);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((amount == null) ? 0 : amount.hashCode());
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
    Money other = (Money) obj;
    if (amount == null) {
      if (other.amount != null)
        return false;
    } else if (!amount.equals(other.amount))
      return false;
    return true;
  }

}
