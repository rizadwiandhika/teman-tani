package com.temantani.investment.service.dataaccess.postgresql.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.UserId;
import com.temantani.investment.service.dataaccess.postgresql.mapper.InvestmentDataAccessMapper;
import com.temantani.investment.service.dataaccess.postgresql.repository.InvestorJpaRepository;
import com.temantani.investment.service.domain.entity.Investor;
import com.temantani.investment.service.domain.ports.output.repository.InvestorRepository;

@Component
public class InvestorRepositoryImpl implements InvestorRepository {

  private final InvestorJpaRepository repo;
  private final InvestmentDataAccessMapper mapper;

  public InvestorRepositoryImpl(InvestorJpaRepository repo, InvestmentDataAccessMapper mapper) {
    this.repo = repo;
    this.mapper = mapper;
  }

  @Override
  public Optional<Investor> findById(UserId investorId) {
    return repo.findById(investorId.getValue()).map(mapper::investorEntityToInvestor);
  }

  @Override
  public Investor save(Investor investor) {
    return mapper.investorEntityToInvestor(repo.save(mapper.investorToInvestorEntity(investor)));
  }

}
