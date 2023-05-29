package com.temantani.project.service.domain.valueobject;

import java.math.BigDecimal;

import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.domain.exception.ProjectDomainException;

public class ProfitReceiver {

  private final BigDecimal devidend;
  private final UserId receiverId;
  private final DistributionType type;

  public UserId getReceiverId() {
    return receiverId;
  }

  public BigDecimal getDevidend() {
    return devidend;
  }

  public DistributionType getType() {
    return type;
  }

  public static ProfitReceiver makeLandowner(UserId receiverId) {
    return new Builder()
        .type(DistributionType.LANDOWNER)
        .deviden(BigDecimal.valueOf(0.15))
        .receiverId(receiverId)
        .build();
  }

  public static Builder builder() {
    return new Builder();
  }

  /**
   * 
   * @param investorId
   * @param deviden    is investment / total investment
   * @return ProfitReceiver
   */
  public static ProfitReceiver makeInvestor(UserId investorId, BigDecimal deviden) {
    return new Builder()
        .type(DistributionType.INVESTOR)
        .deviden(deviden)
        .receiverId(investorId)
        .build();
  }

  private ProfitReceiver(Builder builder) {
    if (builder.deviden.compareTo(BigDecimal.valueOf(0)) == -1
        || builder.deviden.compareTo(BigDecimal.valueOf(1)) == 1) {
      throw new ProjectDomainException("percentage is not valid since it is not between 0 - 100");
    }

    receiverId = builder.receiverId;
    devidend = builder.deviden;
    type = builder.type;
  }

  public static class Builder {
    private UserId receiverId;
    private BigDecimal deviden;
    private DistributionType type;

    public Builder receiverId(UserId receiverId) {
      this.receiverId = receiverId;
      return this;
    }

    public Builder deviden(BigDecimal deviden) {
      this.deviden = deviden;
      return this;
    }

    public Builder type(DistributionType type) {
      this.type = type;
      return this;
    }

    public ProfitReceiver build() {
      return new ProfitReceiver(this);
    }
  }

}
