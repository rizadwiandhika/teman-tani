package com.temantani.project.service.domain.entity;

import com.temantani.domain.entity.AggregateRoot;
import com.temantani.domain.valueobject.Address;
import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.domain.exception.ProjectDomainException;
import com.temantani.project.service.domain.valueobject.LandAvailabilityStatus;

public class Land extends AggregateRoot<LandId> {

  private final UserId ownerId;
  private Address address;
  private LandAvailabilityStatus availabilityStatus;

  public void reserveLand() {
    if (availabilityStatus != LandAvailabilityStatus.AVAILABLE) {
      throw new ProjectDomainException("Land is not available for reservation");
    }

    availabilityStatus = LandAvailabilityStatus.RESERVED;
  }

  public void releaseLand() {
    if (availabilityStatus != LandAvailabilityStatus.RESERVED) {
      throw new ProjectDomainException("Land is not reserved");
    }

    availabilityStatus = LandAvailabilityStatus.AVAILABLE;
  }

  public UserId getOwnerId() {
    return ownerId;
  }

  public Address getAddress() {
    return address;
  }

  public LandAvailabilityStatus getAvailabilityStatus() {
    return availabilityStatus;
  }

  public static Builder builder() {
    return new Builder();
  }

  private Land(Builder builder) {
    super.setId(builder.id);
    super.setVersion(builder.version);
    this.ownerId = builder.ownerId;
    this.address = builder.address;
    this.availabilityStatus = builder.landStatus;
  }

  // Builder for Land class
  public static class Builder {
    private LandId id;
    private Integer version;

    private UserId ownerId;
    private Address address;
    private LandAvailabilityStatus landStatus;

    public Builder() {
    }

    public Builder id(LandId id) {
      this.id = id;
      return this;
    }

    public Builder version(Integer version) {
      this.version = version;
      return this;
    }

    public Builder ownerId(UserId ownerId) {
      this.ownerId = ownerId;
      return this;
    }

    public Builder address(Address address) {
      this.address = address;
      return this;
    }

    public Builder landStatus(LandAvailabilityStatus landStatus) {
      this.landStatus = landStatus;
      return this;
    }

    public Land build() {
      return new Land(this);
    }
  }

}
