package com.temantani.domain.valueobject;

public class Address {

  // private UUID id;

  private final String street;
  private final String city;
  private final String postalCode;

  // public Address(UUID id, String street, String city, String postalCode) {
  // this.id = id;
  // this.street = street;
  // this.city = city;
  // this.postalCode = postalCode;
  // }

  public Address(String street, String city, String postalCode) {
    this.street = street;
    this.city = city;
    this.postalCode = postalCode;
  }

  // public void setId(UUID id) {
  // this.id = id;
  // }

  // public UUID getId() {
  // return id;
  // }

  public String getStreet() {
    return street;
  }

  public String getCity() {
    return city;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public Boolean isComplete() {
    Boolean nonNull = this.street != null && this.city != null && this.postalCode != null;
    if (!nonNull) {
      return false;
    }

    Boolean nonEmpty = !this.street.isEmpty() && !this.city.isEmpty() && !this.postalCode.isEmpty();
    if (!nonEmpty) {
      return false;
    }

    Boolean nonBlank = !this.street.trim().isEmpty() && !this.city.trim().isEmpty()
        && !this.postalCode.trim().isEmpty();
    if (!nonBlank) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((street == null) ? 0 : street.hashCode());
    result = prime * result + ((city == null) ? 0 : city.hashCode());
    result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
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
    Address other = (Address) obj;
    if (street == null) {
      if (other.street != null)
        return false;
    } else if (!street.equals(other.street))
      return false;
    if (city == null) {
      if (other.city != null)
        return false;
    } else if (!city.equals(other.city))
      return false;
    if (postalCode == null) {
      if (other.postalCode != null)
        return false;
    } else if (!postalCode.equals(other.postalCode))
      return false;
    return true;
  }

}
