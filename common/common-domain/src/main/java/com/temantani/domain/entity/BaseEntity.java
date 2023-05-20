package com.temantani.domain.entity;

public abstract class BaseEntity<ID> {

  private ID id;

  public ID getId() {
    return id;
  }

  public void setId(ID id) {
    this.id = id;
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
