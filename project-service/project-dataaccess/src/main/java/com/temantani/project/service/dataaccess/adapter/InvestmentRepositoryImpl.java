package com.temantani.project.service.dataaccess.adapter;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;

import com.temantani.domain.exception.DataAlreadyExistsException;
import com.temantani.project.service.dataaccess.mapper.ProjectDataAccessMapper;
import com.temantani.project.service.dataaccess.repository.InvestmentJpaRepository;
import com.temantani.project.service.domain.entity.Investment;
import com.temantani.project.service.domain.ports.output.repository.InvestmentRepository;

@Component
public class InvestmentRepositoryImpl implements InvestmentRepository {

  private final EntityManager manager;
  private final InvestmentJpaRepository repo;
  private final ProjectDataAccessMapper mapper;

  public InvestmentRepositoryImpl(EntityManager manager, InvestmentJpaRepository repo, ProjectDataAccessMapper mapper) {
    this.manager = manager;
    this.repo = repo;
    this.mapper = mapper;
  }

  @Override
  public Investment create(Investment investment) throws DataAlreadyExistsException {
    try {
      manager.persist(mapper.investmentToInvestmentEntity(investment));
      manager.flush();

      return repo.findById(investment.getId().getValue()).map(mapper::investmentEntityToInvestment).orElse(null);
    } catch (EntityExistsException e) {
      throw new DataAlreadyExistsException("Investment already exists for: " + investment.getId().getValue(), e);
    }
  }

  @Override
  public List<Investment> saveAll(List<Investment> investments) {
    return repo.saveAll(investments.stream().map(mapper::investmentToInvestmentEntity).collect(Collectors.toList()))
        .stream().map(mapper::investmentEntityToInvestment).collect(Collectors.toList());
  }

}
