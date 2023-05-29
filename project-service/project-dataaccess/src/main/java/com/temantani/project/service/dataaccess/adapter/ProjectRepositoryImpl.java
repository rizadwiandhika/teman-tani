package com.temantani.project.service.dataaccess.adapter;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.temantani.domain.valueobject.ProjectId;
import com.temantani.project.service.dataaccess.mapper.ProjectDataAccessMapper;
import com.temantani.project.service.dataaccess.repository.ProjectJpaRepository;
import com.temantani.project.service.domain.entity.Project;
import com.temantani.project.service.domain.ports.output.repository.ProjectRepository;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

  private final ProjectJpaRepository repo;
  private final ProjectDataAccessMapper mapper;

  public ProjectRepositoryImpl(ProjectJpaRepository repo, ProjectDataAccessMapper mapper) {
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
