package com.temantani.project.service.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.domain.exception.DataNotFoundException;
import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.project.service.domain.dto.query.ProjectData;
import com.temantani.project.service.domain.dto.query.ProjectDataDetails;
import com.temantani.project.service.domain.dto.query.TrackProfitDistributionResponse;
import com.temantani.project.service.domain.mapper.ProjectDataMapper;
import com.temantani.project.service.domain.ports.input.service.ProjectQueryService;
import com.temantani.project.service.domain.ports.output.repository.ProfitDistributionRepository;
import com.temantani.project.service.domain.ports.output.repository.ProjectRepository;

@Service
@Transactional(readOnly = true)
public class ProjectQueryServiceImpl implements ProjectQueryService {

  private final ProjectRepository projectRepository;
  private final ProfitDistributionRepository profitDistributionRepository;
  private final ProjectDataMapper mapper;

  public ProjectQueryServiceImpl(ProjectRepository projectRepository,
      ProfitDistributionRepository profitDistributionRepository, ProjectDataMapper mapper) {
    this.projectRepository = projectRepository;
    this.profitDistributionRepository = profitDistributionRepository;
    this.mapper = mapper;
  }

  @Override
  public List<TrackProfitDistributionResponse> getProfitDistributionsOf(ProjectId projectId) {
    return profitDistributionRepository.findByProjectId(projectId)
        .map(l -> l.stream()
            .map(mapper::profitDistributionToTrackProfitDistributionResponse)
            .collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }

  @Override
  public ProjectDataDetails getProjectDetails(ProjectId projectId) throws DataNotFoundException {
    return projectRepository.findById(projectId)
        .map(mapper::projectToProjectDataDetails)
        .orElseThrow(() -> new DataNotFoundException("Project: " + projectId.getValue() + " not found"));
  }

  @Override
  public List<ProjectData> getProjects() {
    return projectRepository.findAll()
        .map(projects -> projects.stream().map(mapper::projectToProjectData).collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }

  @Override
  public List<ProjectData> getProjectsOf(LandId landId) {
    return projectRepository.findByLandId(landId)
        .map(projects -> projects.stream().map(mapper::projectToProjectData).collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }

}
