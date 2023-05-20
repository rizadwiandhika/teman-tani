package com.temantani.land.service.dataaccess.borrower.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.dataaccess.borrower.mapper.BorrowerDataAccessMapper;
import com.temantani.land.service.dataaccess.borrower.repository.BorrowerJpaRepository;
import com.temantani.land.service.domain.entity.Borrower;
import com.temantani.land.service.domain.ports.output.repository.BorrowerRepository;

@Component
public class BorrowerRepositoryImpl implements BorrowerRepository {

  private final BorrowerJpaRepository borrowerJpaRepository;
  private final BorrowerDataAccessMapper mapper;

  public BorrowerRepositoryImpl(BorrowerJpaRepository borrowerJpaRepository, BorrowerDataAccessMapper mapper) {
    this.borrowerJpaRepository = borrowerJpaRepository;
    this.mapper = mapper;
  }

  @Override
  public Optional<Borrower> findById(UserId borrowerId) {
    return borrowerJpaRepository.findById(borrowerId.getValue()).map(mapper::borrowerEntityToBorrower);
  }

}