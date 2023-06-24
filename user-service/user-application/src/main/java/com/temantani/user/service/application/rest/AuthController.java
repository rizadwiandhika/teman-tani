package com.temantani.user.service.application.rest;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.temantani.security.service.JwtService;
import com.temantani.user.service.domain.dto.login.UserLoginRequest;
import com.temantani.user.service.domain.dto.login.UserLoginResponse;
import com.temantani.user.service.domain.dto.registration.UserRegistrationRequest;
import com.temantani.user.service.domain.dto.registration.UserRegistrationResponse;
import com.temantani.user.service.domain.ports.input.service.UserApplicationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/auth", produces = "application/json")
public class AuthController {

  private final UserApplicationService userApplicationService;
  private final AuthenticationManager authManager;
  private final JwtService jwtService;

  public AuthController(UserApplicationService userApplicationService, AuthenticationManager authManager,
      JwtService jwtService) {
    this.userApplicationService = userApplicationService;
    this.authManager = authManager;
    this.jwtService = jwtService;
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<UserRegistrationResponse> register(@RequestBody @Valid UserRegistrationRequest request) {
    log.info("Received registration request of user email: {}", request.getEmail());
    return ResponseEntity.ok(userApplicationService.registerUser(request));
  }

  @PostMapping("/login")
  public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest request) {
    log.info("Received login request of user email: {}", request.getEmail());

    UsernamePasswordAuthenticationToken auth = UsernamePasswordAuthenticationToken.unauthenticated(
        request.getEmail(),
        request.getPassword());

    Authentication authenticatedUser = authManager.authenticate(auth);

    String token = jwtService.generateToken((UserDetails) authenticatedUser.getPrincipal());

    UserLoginResponse response = UserLoginResponse.builder()
        .token(token)
        .message("Login successful")
        .build();

    return ResponseEntity.ok(response);
  }

}
