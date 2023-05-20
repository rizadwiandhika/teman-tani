package com.temantani.investment.service.dataaccess.postgresql.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.ProjectId;
import com.temantani.investment.service.dataaccess.postgresql.mapper.InvestmentDataAccessMapper;
import com.temantani.investment.service.dataaccess.postgresql.repository.ProjectJpaRepository;
import com.temantani.investment.service.domain.entity.Project;
import com.temantani.investment.service.domain.ports.output.repository.ProjectRepository;

@Component
public class ProjectRepositoryImpl implements ProjectRepository {

  private final ProjectJpaRepository repo;
  private final InvestmentDataAccessMapper mapper;

  public ProjectRepositoryImpl(ProjectJpaRepository repo, InvestmentDataAccessMapper mapper) {
    this.repo = repo;
    this.mapper = mapper;
  }

  @Override
  public Optional<Project> findById(ProjectId projectId) {
    return repo.findById(projectId.getValue()).map(mapper::projectEntityToProject);
  }

  @Override
  public Project save(Project project) {
    return mapper.projectEntityToProject(repo.save(mapper.projectToProjectEntity(project)));
  }

}
