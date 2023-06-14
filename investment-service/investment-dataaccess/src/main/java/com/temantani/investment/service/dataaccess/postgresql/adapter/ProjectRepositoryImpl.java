package com.temantani.investment.service.dataaccess.postgresql.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;

import com.temantani.domain.exception.DataAlreadyExistsException;
import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.investment.service.dataaccess.postgresql.mapper.InvestmentDataAccessMapper;
import com.temantani.investment.service.dataaccess.postgresql.repository.ProjectJpaRepository;
import com.temantani.investment.service.domain.entity.Fundraising;
import com.temantani.investment.service.domain.ports.output.repository.FundraisingRepository;
import com.temantani.investment.service.domain.valueobject.FundraisingStatus;

@Component
public class ProjectRepositoryImpl implements FundraisingRepository {

  private final ProjectJpaRepository repo;
  private final InvestmentDataAccessMapper mapper;
  private final EntityManager manager;

  public ProjectRepositoryImpl(ProjectJpaRepository repo, InvestmentDataAccessMapper mapper, EntityManager manager) {
    this.repo = repo;
    this.mapper = mapper;
    this.manager = manager;
  }

  @Override
  public Optional<Fundraising> findById(ProjectId projectId) {
    return repo.findById(projectId.getValue()).map(mapper::projectEntityToProject);
  }

  @Override
  public Fundraising save(Fundraising project) {
    return mapper.projectEntityToProject(repo.save(mapper.projectToProjectEntity(project)));
  }

  @Override
  public Optional<Fundraising> findByInvestmentId(InvestmentId investmentId) {
    return repo.findByInvestmentId(investmentId.getValue()).map(mapper::projectEntityToProject);
  }

  @Override
  public Optional<List<Fundraising>> findByStatus(FundraisingStatus status) {
    return repo.findByStatus(status).map(l -> l.stream()
        .map(mapper::projectEntityToProject).collect(Collectors.toList()));
  }

  @Override
  public Fundraising create(Fundraising project) {
    try {
      manager.persist(mapper.projectToProjectEntity(project));
      manager.flush();
      return findById(project.getId()).orElse(null);
    } catch (EntityExistsException e) {
      throw new DataAlreadyExistsException(
          "Project: " + project.getId().getValue() + " is already exists for fundraising", e);
    } /*
       * catch (PersistenceException e) {
       * 
       * }
       */
  }

}
