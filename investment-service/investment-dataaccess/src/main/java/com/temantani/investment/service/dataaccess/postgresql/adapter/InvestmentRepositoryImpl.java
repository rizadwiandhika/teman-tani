package com.temantani.investment.service.dataaccess.postgresql.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.investment.service.dataaccess.postgresql.entity.InvestmentEntity;
import com.temantani.investment.service.dataaccess.postgresql.entity.FundraisingEntity;
import com.temantani.investment.service.dataaccess.postgresql.mapper.InvestmentDataAccessMapper;
import com.temantani.investment.service.dataaccess.postgresql.repository.InvestmentJpaRepository;
import com.temantani.investment.service.domain.entity.Investment;
import com.temantani.investment.service.domain.ports.output.repository.InvestmentRepository;
import com.temantani.investment.service.domain.valueobject.InvestmentStatus;

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
    return repo.findById(investmentId.getValue())
        .map((i) -> mapper.investmentEntityToInvestment(i, i.getFundraising().getId()));
  }

  @Override
  public Optional<List<Investment>> findByStatus(InvestmentStatus status) {
    return repo.findByStatus(status)
        .map((l) -> l.stream()
            .map(i -> mapper.investmentEntityToInvestment(i, i.getFundraising().getId()))
            .collect(Collectors.toList()));
  }

  @Override
  public void saveAll(List<Investment> investments) {
    List<InvestmentEntity> entities = investments.stream().map((i) -> {
      InvestmentEntity e = mapper.investmentToInvestmentEntity(i);
      e.setFundraising(FundraisingEntity.builder().id(i.getProjectId().getValue()).build());
      return e;
    }).collect(Collectors.toList());

    repo.saveAll(entities);
  }

  @Override
  public Optional<List<Investment>> findByInvestorId(UserId investorId) {
    return repo.findByInvestorId(investorId.getValue())
        .map((l) -> l.stream()
            .map(i -> mapper.investmentEntityToInvestment(i, i.getFundraising().getId()))
            .collect(Collectors.toList()));
  }

  // @Override
  // public Investment save(Investment investment) {
  // return
  // mapper.investmentEntityToInvestment(repo.save(mapper.investmentToInvestmentEntity(investment)));
  // }

}
