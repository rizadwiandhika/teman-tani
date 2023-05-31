package com.temantani.investment.service.domain.ports.output.repository;

import java.util.List;
import java.util.Optional;

import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.investment.service.domain.entity.Project;
import com.temantani.investment.service.domain.valueobject.ProjectStatus;

public interface ProjectRepository {

  Optional<List<Project>> findByStatus(ProjectStatus status);

  Optional<Project> findById(ProjectId projectId);

  Optional<Project> findByInvestmentId(InvestmentId investmentId);

  Project save(Project project);

  Project create(Project project);

  // List<Project> saveAll(List<Project> project);

}
