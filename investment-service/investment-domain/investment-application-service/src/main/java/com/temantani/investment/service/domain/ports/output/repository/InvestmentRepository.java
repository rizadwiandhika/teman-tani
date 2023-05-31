package com.temantani.investment.service.domain.ports.output.repository;

import java.util.Optional;

import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.investment.service.domain.entity.Investment;

public interface InvestmentRepository {

  Optional<Investment> findById(InvestmentId investmentId);

  // Investment save(Investment investment);

}
