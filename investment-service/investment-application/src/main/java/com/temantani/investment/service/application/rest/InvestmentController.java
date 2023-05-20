package com.temantani.investment.service.application.rest;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.temantani.domain.dto.BasicResponse;
import com.temantani.domain.valueobject.UserId;
import com.temantani.investment.service.domain.dto.CreateInvestmentRequest;
import com.temantani.investment.service.domain.dto.InvestmentBasicResponse;
import com.temantani.investment.service.domain.ports.input.service.InvestmentApplicationService;

// TODO: secure request to "/investments" to be authenticated with Spring Security
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/investments", produces = "application/json")
public class InvestmentController {

  private final InvestmentApplicationService applicationService;

  public InvestmentController(InvestmentApplicationService applicationService) {
    this.applicationService = applicationService;
  }

  @PostMapping("")
  public ResponseEntity<InvestmentBasicResponse> createInvestment(
      @RequestBody @Valid CreateInvestmentRequest request,
      Authentication auth) {
    request.setInvestorId(getAuthenticatedUserId(auth));
    return ResponseEntity.ok(applicationService.createInvestment(request));
  }

  private UserId getAuthenticatedUserId(Authentication auth) {
    UserDetails userDetails = (UserDetails) auth.getPrincipal();
    return new UserId(UUID.fromString(userDetails.getUsername()));
  }

}
