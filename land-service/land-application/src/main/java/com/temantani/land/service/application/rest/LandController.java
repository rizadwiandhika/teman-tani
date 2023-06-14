package com.temantani.land.service.application.rest;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.temantani.domain.dto.BasicResponse;
import com.temantani.domain.ports.output.storage.StorageService;
import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.land.service.domain.dto.proposal.ProposalRequest;
import com.temantani.land.service.domain.dto.query.LandData;
import com.temantani.land.service.domain.dto.query.LandDetailsData;
import com.temantani.land.service.domain.dto.revision.RevisionRequest;
import com.temantani.land.service.domain.ports.input.service.LandApplicationService;
import com.temantani.land.service.domain.ports.input.service.LandQueryService;

@RestController
@RequestMapping(value = "/lands", produces = "application/json")
public class LandController {

  private final LandApplicationService landApplicationService;
  private final LandQueryService landQueryService;
  private final StorageService storageService;

  public LandController(LandApplicationService landApplicationService, LandQueryService landQueryService,
      StorageService storageService) {
    this.landApplicationService = landApplicationService;
    this.landQueryService = landQueryService;
    this.storageService = storageService;
  }

  @PostMapping("")
  public ResponseEntity<BasicResponse> propose(
      @ModelAttribute @Valid ProposalRequest request,
      @RequestParam("certificate") MultipartFile certificate,
      @RequestParam("photo") MultipartFile photo,
      Authentication auth) throws IOException {
    UserId borrowerId = getAuthenticatedUserId(auth);
    String certificatePath = storageService.save(certificate.getInputStream(),
        Path.of(certificate.getOriginalFilename()));
    String photoPath = storageService.save(photo.getInputStream(), Path.of(photo.getOriginalFilename()));

    request.setBorrowerId(borrowerId);
    request.setCertificateUrl(certificatePath);
    request.setPhotoUrl(photoPath);

    return ResponseEntity.ok(landApplicationService.createProposal(request));
  }

  @PutMapping("/{land_id}/revise")
  public ResponseEntity<BasicResponse> revise(
      @ModelAttribute @Valid RevisionRequest request,
      @PathVariable("land_id") UUID landId,
      @RequestParam("certificate") MultipartFile certificate,
      @RequestParam("photo") MultipartFile photo,
      Authentication auth) throws IOException {
    String certificatePath = storageService.save(certificate.getInputStream(),
        Path.of(certificate.getOriginalFilename()));
    String photoPath = storageService.save(photo.getInputStream(), Path.of(photo.getOriginalFilename()));

    request.setLandId(new LandId(landId));
    request.setCertificateUrl(certificatePath);
    request.setPhotoUrl(photoPath);

    return ResponseEntity.ok(landApplicationService.revise(request, getAuthenticatedUserId(auth)));
  }

  @PutMapping("/{land_id}/cancel")
  public ResponseEntity<BasicResponse> cancel(@PathVariable("land_id") UUID landId, Authentication auth) {
    return ResponseEntity.ok(landApplicationService.cancel(new LandId(landId), getAuthenticatedUserId(auth)));
  }

  @GetMapping("")
  public ResponseEntity<List<LandData>> getUserOwnedLands(Authentication auth) {
    UserId userId = getAuthenticatedUserId(auth);
    return ResponseEntity.ok(landQueryService.getUserOwnedLands(userId));
  }

  @GetMapping("/{land_id}")
  public ResponseEntity<LandDetailsData> land(@PathVariable("land_id") UUID landId, Authentication auth) {
    UserId userId = getAuthenticatedUserId(auth);
    return ResponseEntity.ok(landQueryService.getUserLandDetails(userId, new LandId(landId)));
  }

  private UserId getAuthenticatedUserId(Authentication auth) {
    UserDetails userDetails = (UserDetails) auth.getPrincipal();
    return new UserId(UUID.fromString(userDetails.getUsername()));
  }

}
