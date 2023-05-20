package com.temantani.investment.service.dataaccess.postgresql.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.investment.service.dataaccess.postgresql.mapper.InvestmentDataAccessMapper;
import com.temantani.investment.service.dataaccess.postgresql.repository.InvestmentJpaRepository;
import com.temantani.investment.service.domain.entity.Investment;
import com.temantani.investment.service.domain.ports.output.repository.InvestmentRepository;

@Component
public class InvestmentRepositoryImpl implements InvestmentRepository {

  private final InvestmentJpaRepository repo;
  private final InvestmentDataAccessMapper mapper;

  public InvestmentRepositoryImpl(InvestmentJpaRepository repo, InvestmentDataAccessMapper mapper) {
    this.repo = repo;
    this.mapper = mapper;
  }

  @Override
  public Optional<Investment> findById(InvestmentId investmentId) {
    return repo.findById(investmentId.getValue()).map(mapper::investmentEntityToInvestment);
  }

  @Override
  public Investment save(Investment investment) {
    return mapper.investmentEntityToInvestment(repo.save(mapper.investmentToInvestmentEntity(investment)));
  }

}
