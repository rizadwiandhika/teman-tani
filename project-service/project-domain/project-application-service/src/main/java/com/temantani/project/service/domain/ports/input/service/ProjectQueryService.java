package com.temantani.project.service.domain.ports.input.service;

import java.util.List;

import com.temantani.domain.exception.DataNotFoundException;
import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.project.service.domain.dto.query.*;

public interface ProjectQueryService {

  List<ProjectData> getProjectsOf(LandId landId);

  List<ProjectData> getProjects();

  ProjectDataDetails getProjectDetails(ProjectId projectId) throws DataNotFoundException;

  List<TrackProfitDistributionResponse> getProfitDistributionsOf(ProjectId projectId);

}
