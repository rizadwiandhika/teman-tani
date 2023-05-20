package com.temantani.user.service.dataaccess.user.entity;

import java.io.Serializable;

import com.temantani.domain.valueobject.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor // required by Springboot
@AllArgsConstructor // required for builder pattern
public class RoleEntityId implements Serializable {

  private UserEntity user;
  private UserRole role;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((user == null) ? 0 : user.hashCode());
    result = prime * result + ((role == null) ? 0 : role.hashCode());
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
    RoleEntityId other = (RoleEntityId) obj;
    if (user == null) {
      if (other.user != null)
        return false;
    } else if (!user.equals(other.user))
      return false;
    if (role != other.role)
      return false;
    return true;
  }

}
