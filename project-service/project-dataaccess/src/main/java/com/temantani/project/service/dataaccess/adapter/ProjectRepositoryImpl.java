package com.temantani.project.service.dataaccess.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.project.service.dataaccess.entity.ProjectEntity;
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

  @Override
  public Optional<List<Project>> findAll() {
    List<ProjectEntity> projects = repo.findAll();
    if (projects == null) {
      return Optional.empty();
    }

    return Optional.of(projects.stream().map(mapper::projectEntityToProject).collect(Collectors.toList()));
  }

  @Override
  public Optional<List<Project>> findByLandId(LandId landId) {
    return repo.findByLandId(landId.getValue())
        .map(l -> l.stream().map(mapper::projectEntityToProject).collect(Collectors.toList()));
  }

}
