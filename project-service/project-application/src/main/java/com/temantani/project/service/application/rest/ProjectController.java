package com.temantani.project.service.application.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.project.service.domain.dto.query.ProjectData;
import com.temantani.project.service.domain.dto.query.ProjectDataDetails;
import com.temantani.project.service.domain.dto.query.TrackProfitDistributionResponse;
import com.temantani.project.service.domain.ports.input.service.ProjectQueryService;

@RestController
@CrossOrigin
@RequestMapping(produces = "application/json")
public class ProjectController {

  private final ProjectQueryService projectQueryService;

  public ProjectController(ProjectQueryService projectQueryService) {
    this.projectQueryService = projectQueryService;
  }

  @GetMapping("/lands/{land_id}/projects")
  public ResponseEntity<List<ProjectData>> getProjectsOfLand(@PathVariable("land_id") UUID landId,
      Authentication auth) {
    return ResponseEntity.ok(projectQueryService.getProjectsOf(new LandId(landId)));
  }

  @GetMapping("/projects/{project_id}")
  public ResponseEntity<ProjectDataDetails> getProjectDetails(@PathVariable("project_id") UUID projectId) {
    return ResponseEntity.ok(projectQueryService.getProjectDetails(new ProjectId(projectId)));
  }

  @GetMapping("/projects/{project_id}/profit-distributions")
  public ResponseEntity<List<TrackProfitDistributionResponse>> getProjectProfitDistributions(
      @PathVariable("project_id") UUID projectId) {
    return ResponseEntity.ok(projectQueryService.getProfitDistributionsOf(new ProjectId(projectId)));
  }

}
