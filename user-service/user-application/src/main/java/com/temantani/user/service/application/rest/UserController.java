package com.temantani.user.service.application.rest;

import java.io.IOException;
import java.nio.file.Path;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.temantani.domain.dto.BasicResponse;
import com.temantani.domain.ports.output.storage.StorageService;
import com.temantani.domain.valueobject.UserId;
import com.temantani.security.service.JwtService;
import com.temantani.user.service.application.security.UserDetailsImpl;
import com.temantani.user.service.domain.dto.passwordchange.PasswordChangeRequest;
import com.temantani.user.service.domain.dto.roleactivation.RoleActivationRequest;
import com.temantani.user.service.domain.dto.roleactivation.RoleActivationResponse;
import com.temantani.user.service.domain.dto.track.UserDetailTrackResponse;
import com.temantani.user.service.domain.dto.updateprofile.UpdateProfileRequest;
import com.temantani.user.service.domain.ports.input.service.UserApplicationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/users", produces = "application/vnd.api.v1+json")
public class UserController {

  private final UserApplicationService userApplicationService;
  private final StorageService storageService;
  private final JwtService jwtService;

  public UserController(UserApplicationService userApplicationService, StorageService storageService,
      JwtService jwtService) {
    this.userApplicationService = userApplicationService;
    this.storageService = storageService;
    this.jwtService = jwtService;
  }

  @PutMapping("/{user_id}")
  public ResponseEntity<BasicResponse> updateProfile(@RequestBody @Valid UpdateProfileRequest request,
      @PathVariable("user_id") UUID userUUID, Authentication auth) {
    UserId initiatorId = getAuthenticatedUserId(auth);
    UserId userId = new UserId(userUUID);

    log.info("Received update profile request for user : {} by {}", userId.getValue(), initiatorId.getValue());

    return ResponseEntity.ok(userApplicationService.updateProfile(initiatorId, userId, request));
  }

  @PostMapping("/{user_id}/activate-role")
  public ResponseEntity<RoleActivationResponse> activateRole(
      @ModelAttribute @Valid RoleActivationRequest request,
      @RequestParam("identityCard") MultipartFile identityCard,
      @PathVariable("user_id") UUID userUUID,
      Authentication auth)
      throws IOException {
    String path = storageService.save(identityCard.getInputStream(), Path.of(identityCard.getOriginalFilename()));
    request.setIdentityCardUrl(path);

    UserDetails user = (UserDetails) auth.getPrincipal();
    UserId initiatorId = new UserId(UUID.fromString(user.getUsername()));
    UserId userId = new UserId(userUUID);

    RoleActivationResponse response = userApplicationService.activateRole(initiatorId, userId, request);
    UserDetails updatedUser = UserDetailsImpl.builder()
        .username(user.getUsername())
        .roles(response.getRoles())
        .build();

    String token = jwtService.generateToken(updatedUser);
    response.setToken(token);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/me")
  public ResponseEntity<UserDetailTrackResponse> me(Authentication auth) {
    UserId userId = getAuthenticatedUserId(auth);

    return ResponseEntity.ok(userApplicationService.trackUserDetail(userId, userId));
  }

  @PutMapping("/password")
  public ResponseEntity<BasicResponse> updatePassword(
      @RequestBody @Valid PasswordChangeRequest request, Authentication auth) {
    UserId userId = getAuthenticatedUserId(auth);

    return ResponseEntity
        .ok(userApplicationService.changePassword(userId, request.getCurrentPassword(), request.getNewPassword()));
  }

  private UserId getAuthenticatedUserId(Authentication auth) {
    UserDetails userDetails = (UserDetails) auth.getPrincipal();
    return new UserId(UUID.fromString(userDetails.getUsername()));
  }

}
