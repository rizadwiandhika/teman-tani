package com.temantani.land.service.dataaccess.borrower.mapper;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.dataaccess.borrower.entity.BorrowerEntity;
import com.temantani.land.service.domain.entity.Borrower;

@Component
public class BorrowerDataAccessMapper {

  public Borrower borrowerEntityToBorrower(BorrowerEntity borrowerEntity) {
    return Borrower.builder()
        .id(new UserId(borrowerEntity.getId()))
        .email(borrowerEntity.getEmail())
        .name(borrowerEntity.getName())
        .profilePictureUrl(borrowerEntity.getProfilePictureUrl())
        .build();
  }

}
