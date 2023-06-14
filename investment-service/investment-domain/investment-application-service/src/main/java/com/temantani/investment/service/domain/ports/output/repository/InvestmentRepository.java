package com.temantani.investment.service.domain.ports.output.repository;

import java.util.List;
import java.util.Optional;

import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.investment.service.domain.entity.Investment;
import com.temantani.investment.service.domain.valueobject.InvestmentStatus;

public interface InvestmentRepository {

  Optional<Investment> findById(InvestmentId investmentId);

  Optional<List<Investment>> findByInvestorId(UserId investorId);

  Optional<List<Investment>> findByStatus(InvestmentStatus status);

  void saveAll(List<Investment> investments);

}
