package com.temantani.project.service.domain.ports.input.service;

import java.util.Map;

import com.temantani.domain.dto.BasicResponse;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.domain.dto.project.CreateProjectRequest;
import com.temantani.project.service.domain.dto.project.CreateProjectResponse;
import com.temantani.project.service.domain.dto.query.TrackProfitDistributionResponse;
import com.temantani.project.service.domain.valueobject.ProfitDistributionDetailId;
import com.temantani.project.service.domain.valueobject.ProfitDistributionId;
import com.temantani.project.service.domain.dto.project.BaseProjectResponse;
import com.temantani.project.service.domain.dto.project.CreateExpenseRequest;

public interface ProjectApplicationService {

  CreateProjectResponse createProject(UserId managerId, CreateProjectRequest request);

  BaseProjectResponse conitnueProjectToHiring(UserId managerId, ProjectId projectId);

  BaseProjectResponse executeProject(UserId managerId, ProjectId projectId);

  BaseProjectResponse finishProject(UserId managerId, ProjectId projectId);

  BasicResponse addProjectExpense(UserId managerId, ProjectId projectId, CreateExpenseRequest request);

  TrackProfitDistributionResponse generateProfitDistribution(UserId managerId, ProjectId projectId);

  TrackProfitDistributionResponse completeProfitDistribution(UserId managerId,
      ProfitDistributionId profitDistributionId,
      Map<ProfitDistributionDetailId, String> transferProof);
}
