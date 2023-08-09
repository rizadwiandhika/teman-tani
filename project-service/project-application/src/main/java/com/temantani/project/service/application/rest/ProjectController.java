package com.temantani.project.service.application.rest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.core.Authentication;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.kafka.producer.service.KafkaProducer;
import com.temantani.kafka.project.json.model.OrderCreatedJsonModel;
import com.temantani.project.service.domain.dto.query.ProjectData;
import com.temantani.project.service.domain.dto.query.ProjectDataDetails;
import com.temantani.project.service.domain.dto.query.TrackProfitDistributionResponse;
import com.temantani.project.service.domain.ports.input.service.ProjectQueryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(produces = "application/json")
public class ProjectController {

  private final ProjectQueryService projectQueryService;
  private final ObjectMapper objectMapper;
  private final KafkaProducer<String, String> producer;

  public ProjectController(ProjectQueryService projectQueryService, ObjectMapper objectMapper,
      KafkaProducer<String, String> producer) {
    this.projectQueryService = projectQueryService;
    this.objectMapper = objectMapper;
    this.producer = producer;
  }

  @GetMapping("/lands/{land_id}/projects")
  public ResponseEntity<List<ProjectData>> getProjectsOfLand(@PathVariable("land_id") UUID landId,
      Authentication auth) {
    return ResponseEntity.ok(projectQueryService.getProjectsOf(new LandId(landId)));
  }

  @PostMapping("/projects/{project_id}/orders")
  public ResponseEntity<OrderCreatedJsonModel> publishOrder(@PathVariable("project_id") UUID projectId)
      throws JsonProcessingException {
    OrderCreatedJsonModel message = OrderCreatedJsonModel.builder()
        .amount(new BigDecimal(500000))
        .orderId(UUID.randomUUID().toString())
        .projectId(projectId.toString())
        .build();

    String payload = objectMapper.writeValueAsString(message);

    producer.send("order-created", UUID.randomUUID().toString(), payload,
        new ListenableFutureCallback<SendResult<String, String>>() {
          @Override
          public void onFailure(Throwable ex) {
            log.error(ex.getMessage());
          }

          public void onSuccess(SendResult<String, String> result) {
            log.info("Success publishing order created event");
          };

        });
    return ResponseEntity.ok(message);
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
