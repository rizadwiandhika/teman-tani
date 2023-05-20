package com.temantani.investment.service.domain.ports.output.repository;

import java.util.Optional;

import com.temantani.domain.valueobject.ProjectId;
import com.temantani.investment.service.domain.entity.Project;

public interface ProjectRepository {

  Optional<Project> findById(ProjectId projectId);

  Project save(Project project);

}
