package com.temantani.investment.service.domain.ports.output.repository;

import java.util.Optional;

import com.temantani.domain.valueobject.UserId;
import com.temantani.investment.service.domain.entity.Investor;

public interface InvestorRepository {

  Optional<Investor> findById(UserId investorId);

  Investor save(Investor investor);

}
