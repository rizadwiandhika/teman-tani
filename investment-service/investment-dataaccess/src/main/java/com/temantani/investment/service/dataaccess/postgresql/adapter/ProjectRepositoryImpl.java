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
import com.temantani.investment.service.domain.entity.Project;
import com.temantani.investment.service.domain.ports.output.repository.ProjectRepository;
import com.temantani.investment.service.domain.valueobject.ProjectStatus;

@Component
public class ProjectRepositoryImpl implements ProjectRepository {

  private final ProjectJpaRepository repo;
  private final InvestmentDataAccessMapper mapper;
  private final EntityManager manager;

  public ProjectRepositoryImpl(ProjectJpaRepository repo, InvestmentDataAccessMapper mapper, EntityManager manager) {
    this.repo = repo;
    this.mapper = mapper;
    this.manager = manager;
  }

  @Override
  public Optional<Project> findById(ProjectId projectId) {
    return repo.findById(projectId.getValue()).map(mapper::projectEntityToProject);
  }

  @Override
  public Project save(Project project) {
    return mapper.projectEntityToProject(repo.save(mapper.projectToProjectEntity(project)));
  }

  @Override
  public Optional<Project> findByInvestmentId(InvestmentId investmentId) {
    return repo.findByInvestmentId(investmentId.getValue()).map(mapper::projectEntityToProject);
  }

  @Override
  public Optional<List<Project>> findByStatus(ProjectStatus status) {
    return repo.findByStatus(status).map(l -> l.stream()
        .map(mapper::projectEntityToProject).collect(Collectors.toList()));
  }

  @Override
  public Project create(Project project) {
    try {
      manager.persist(mapper.projectToProjectEntity(project));
      manager.flush();
      return findById(project.getId()).orElse(null);
    } catch (EntityExistsException e) {
      throw new DataAlreadyExistsException(
          "Project: " + project.getId().getValue() + " is already exists for fundraising", e);
    }
  }

}
