package com.temantani.project.service.application.rest;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.temantani.domain.dto.BasicResponse;
import com.temantani.domain.ports.output.storage.StorageService;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.domain.dto.project.CreateExpenseRequest;
import com.temantani.project.service.domain.dto.profitdistribution.TrackProfitDistributionResponse;
import com.temantani.project.service.domain.dto.project.BaseProjectResponse;
import com.temantani.project.service.domain.dto.project.CreateProjectRequest;
import com.temantani.project.service.domain.dto.project.CreateProjectResponse;
import com.temantani.project.service.domain.ports.input.service.ProjectApplicationService;
import com.temantani.project.service.domain.valueobject.ProfitDistributionDetailId;
import com.temantani.project.service.domain.valueobject.ProfitDistributionId;

@RestController
@RequestMapping("/admin")
public class AdminController {
  private final ProjectApplicationService applicationService;
  private final StorageService storageService;

  public AdminController(ProjectApplicationService applicationService, StorageService storageService) {
    this.applicationService = applicationService;
    this.storageService = storageService;
  }

  @PostMapping("/projects")
  public ResponseEntity<CreateProjectResponse> createProject(
      @RequestBody @Valid CreateProjectRequest request,
      Authentication auth) {
    UserId managerId = getUserId(auth);
    return ResponseEntity.ok(applicationService.createProject(managerId, request));
  }

  @PutMapping("/projects/{project_id}/hire")
  public ResponseEntity<BaseProjectResponse> continueToHiring(@PathVariable("project_id") UUID projectId,
      Authentication auth) {
    UserId managerId = getUserId(auth);
    return ResponseEntity.ok(applicationService.conitnueProjectToHiring(managerId, new ProjectId(projectId)));
  }

  @PutMapping("/projects/{project_id}/execute")
  public ResponseEntity<BaseProjectResponse> executeProject(@PathVariable("project_id") UUID projectId,
      Authentication auth) {
    UserId managerId = getUserId(auth);
    return ResponseEntity.ok(applicationService.executeProject(managerId, new ProjectId(projectId)));
  }

  @PutMapping("/projects/{project_id}/finish")
  public ResponseEntity<BaseProjectResponse> finishProject(@PathVariable("project_id") UUID projectId,
      Authentication auth) {
    UserId managerId = getUserId(auth);
    return ResponseEntity.ok(applicationService.finishProject(managerId, new ProjectId(projectId)));
  }

  @PostMapping("/projects/{project_id}/expenses")
  public ResponseEntity<BasicResponse> addExpense(
      @RequestBody @Valid CreateExpenseRequest request,
      @PathVariable("project_id") UUID projectId,
      Authentication auth) {
    UserId managerId = getUserId(auth);
    return ResponseEntity.ok(applicationService.addProjectExpense(managerId, new ProjectId(projectId), request));
  }

  @PostMapping("/projects/{project_id}/profit-distributions")
  public ResponseEntity<TrackProfitDistributionResponse> createProfitDistribution(
      @PathVariable("project_id") UUID projectId,
      Authentication auth) {
    UserId managerId = getUserId(auth);
    return ResponseEntity.ok(applicationService.intiateProfitDistribution(managerId, new ProjectId(projectId)));
  }

  @PutMapping("/profit-distributions/{profit_distribution_id}")
  public ResponseEntity<TrackProfitDistributionResponse> transfer(
      @PathVariable("project_id") UUID projectId,
      @PathVariable("profit_distribution_id") UUID profitDistributionId,
      @RequestParam("proofs") List<MultipartFile> files,
      @RequestParam("profitDistributionDetailIds") List<String> profitDistributionDetailIds,
      Authentication auth) throws IOException {
    UserId managerId = getUserId(auth);

    Map<ProfitDistributionDetailId, String> transferProof = new HashMap<>();
    for (int index = 0; index < files.size(); index++) {
      MultipartFile file = files.get(index);
      String location = storageService.saveTemporary(file.getInputStream(), Path.of(file.getOriginalFilename()));
      transferProof.put(new ProfitDistributionDetailId(profitDistributionId), location);
    }

    return ResponseEntity.ok(applicationService.completeProfitDistribution(
        managerId,
        new ProfitDistributionId(profitDistributionId),
        transferProof));
  }

  private UserId getUserId(Authentication auth) {
    UserDetails user = (UserDetails) auth.getPrincipal();
    return new UserId(UUID.fromString(user.getUsername()));
  }

}
