package com.temantani.land.service.application.rest;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.temantani.domain.dto.BasicResponse;
import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.domain.dto.approval.ApprovalRequest;
import com.temantani.land.service.domain.ports.input.service.LandApplicationService;

@RestController
@RequestMapping(value = "/admins/lands", produces = "application/json")
public class AdminLandController {

  public AdminLandController(LandApplicationService landApplicationService) {
    this.landApplicationService = landApplicationService;
  }

  private final LandApplicationService landApplicationService;

  @PutMapping("/{land_id}/revise")
  public ResponseEntity<BasicResponse> markRevision(
      @RequestBody @Valid @NotNull List<String> revisionMessages,
      @PathVariable("land_id") UUID landId,
      Authentication auth) {
    UserId approverId = getAuthenticatedUserId(auth);
    return ResponseEntity.ok(landApplicationService.markRevision(revisionMessages, approverId, new LandId(landId)));
  }

  @PutMapping("/{land_id}/reject")
  public ResponseEntity<BasicResponse> reject(@RequestBody List<String> reasons, @PathVariable("land_id") UUID landId,
      Authentication auth) {
    return ResponseEntity.ok(landApplicationService.reject(new LandId(landId), getAuthenticatedUserId(auth), reasons));
  }

  @PutMapping("/{land_id}/approve")
  public ResponseEntity<BasicResponse> approve(
      @RequestBody @Valid ApprovalRequest request,
      @PathVariable("land_id") UUID landId,
      Authentication auth) {
    return ResponseEntity.ok(landApplicationService.approve(new LandId(landId), getAuthenticatedUserId(auth), request));
  }

  private UserId getAuthenticatedUserId(Authentication auth) {
    UserDetails userDetails = (UserDetails) auth.getPrincipal();
    return new UserId(UUID.fromString(userDetails.getUsername()));
  }

}
