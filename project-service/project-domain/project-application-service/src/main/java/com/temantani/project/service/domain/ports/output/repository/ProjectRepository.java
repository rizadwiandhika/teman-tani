package com.temantani.project.service.domain.ports.output.repository;

import java.util.List;
import java.util.Optional;

import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.project.service.domain.entity.Project;

public interface ProjectRepository {

  Optional<List<Project>> findAll();

  Optional<List<Project>> findByLandId(LandId landId);

  Optional<Project> findById(ProjectId projectId);

  Project save(Project project);

}
