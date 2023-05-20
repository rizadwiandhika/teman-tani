package com.temantani.land.service.domain.ports.output.repository;

import java.util.Optional;

import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.domain.entity.Borrower;

public interface BorrowerRepository {

  Optional<Borrower> findById(UserId borrowerId);

}
