package com.temantani.user.service.application.rest;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.temantani.domain.dto.BasicResponse;
import com.temantani.domain.valueobject.UserId;
import com.temantani.user.service.domain.dto.registration.AdminRegistrationRequest;
import com.temantani.user.service.domain.ports.input.service.UserApplicationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/admins", produces = "application/vnd.api.v1+json")
public class AdminController {
  private final UserApplicationService userApplicationService;

  public AdminController(UserApplicationService userApplicationService) {
    this.userApplicationService = userApplicationService;
  }

  @PostMapping
  public BasicResponse register(Authentication authentication, @RequestBody @Valid AdminRegistrationRequest request) {
    UserId registratorId = getAuthenticatedUserId(authentication);

    log.info("Registering admin with registratorId: {}, request: {}", registratorId, request);

    return userApplicationService.registerAdmin(registratorId, request);
  }

  private UserId getAuthenticatedUserId(Authentication authentication) {
    UserDetails user = (UserDetails) authentication.getPrincipal();
    return new UserId(UUID.fromString(user.getUsername()));
  }

}
