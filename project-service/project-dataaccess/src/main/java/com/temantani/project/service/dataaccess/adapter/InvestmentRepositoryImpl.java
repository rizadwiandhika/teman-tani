package com.temantani.project.service.dataaccess.adapter;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.temantani.domain.exception.DataAlreadyExistsException;
import com.temantani.project.service.dataaccess.mapper.ProjectDataAccessMapper;
import com.temantani.project.service.dataaccess.repository.InvestmentJpaRepository;
import com.temantani.project.service.domain.entity.Investment;
import com.temantani.project.service.domain.ports.output.repository.InvestmentRepository;

@Repository
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

}
