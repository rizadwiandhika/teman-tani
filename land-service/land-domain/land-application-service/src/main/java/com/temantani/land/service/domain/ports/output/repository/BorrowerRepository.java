package com.temantani.land.service.domain.ports.output.repository;

import java.util.Optional;

import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.domain.entity.Borrower;
import com.temantani.land.service.domain.exception.DataAlreadyExists;

public interface BorrowerRepository {

  Optional<Borrower> findById(UserId borrowerId);

  Borrower save(Borrower borrower) throws DataAlreadyExists;

}
