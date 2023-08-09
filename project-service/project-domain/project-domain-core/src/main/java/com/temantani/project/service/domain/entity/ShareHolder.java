package com.temantani.project.service.domain.entity;

import java.math.BigDecimal;

import com.temantani.domain.entity.BaseEntity;
import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.domain.exception.ProjectDomainException;
import com.temantani.project.service.domain.valueobject.DistributionType;

public class ShareHolder extends BaseEntity<Long> {

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

  public static ShareHolder makeLandowner(UserId receiverId) {
    return new Builder()
        .type(DistributionType.LANDOWNER)
        .deviden(BigDecimal.valueOf(0.10))
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
  public static ShareHolder makeInvestor(UserId investorId, BigDecimal deviden) {
    return new Builder()
        .type(DistributionType.INVESTOR)
        .deviden(deviden)
        .receiverId(investorId)
        .build();
  }

  private ShareHolder(Builder builder) {
    if (builder.deviden.compareTo(BigDecimal.valueOf(0)) == -1
        || builder.deviden.compareTo(BigDecimal.valueOf(1)) == 1) {
      throw new ProjectDomainException("percentage is not valid since it is not between 0 - 100");
    }

    super.setId(builder.id);
    receiverId = builder.receiverId;
    devidend = builder.deviden;
    type = builder.type;
  }

  public static class Builder {
    private Long id;
    private UserId receiverId;
    private BigDecimal deviden;
    private DistributionType type;

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

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

    public ShareHolder build() {
      return new ShareHolder(this);
    }
  }

}
