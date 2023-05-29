package com.temantani.domain.entity;

public abstract class BaseEntity<ID> {

  private ID id;
  private Integer version;

  public ID getId() {
    return id;
  }

  public void setId(ID id) {
    this.id = id;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    BaseEntity<?> that = (BaseEntity<?>) obj;

    return id != null ? id.equals(that.id) : that.id == null;
  }

}
