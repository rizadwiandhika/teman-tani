package com.temantani.land.service.application.rest;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
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
import com.temantani.land.service.domain.dto.revision.RevisionRequest;
import com.temantani.land.service.domain.entity.Land;
import com.temantani.land.service.domain.ports.input.service.LandApplicationService;
import com.temantani.land.service.domain.ports.output.repository.LandRepository;

@RestController
@RequestMapping(value = "/lands", produces = "application/json")
public class LandController {

  private final LandApplicationService landApplicationService;
  private final StorageService storageService;
  private final LandRepository landRepository;

  public LandController(LandApplicationService landApplicationService, StorageService storageService,
      LandRepository landRepository) {
    this.landApplicationService = landApplicationService;
    this.storageService = storageService;
    this.landRepository = landRepository;
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

  @GetMapping("/{land_id}")
  public ResponseEntity<Land> land(@PathVariable("land_id") UUID landId) {
    Optional<Land> landOp = landRepository.findById(new LandId(landId));
    if (landOp.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(landOp.get());
  }

  private UserId getAuthenticatedUserId(Authentication auth) {
    UserDetails userDetails = (UserDetails) auth.getPrincipal();
    return new UserId(UUID.fromString(userDetails.getUsername()));
  }

}
