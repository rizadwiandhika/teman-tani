package com.temantani.investment.service.application.rest;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.temantani.domain.dto.BasicResponse;
import com.temantani.domain.valueobject.UserId;
import com.temantani.investment.service.domain.dto.common.InvestmentBasicResponse;
import com.temantani.investment.service.domain.dto.create.CreateInvestmentRequest;
import com.temantani.investment.service.domain.dto.query.FundraisingData;
import com.temantani.investment.service.domain.dto.query.InvestmentData;
import com.temantani.investment.service.domain.ports.input.service.InvestmentApplicationService;
import com.temantani.investment.service.domain.ports.input.service.InvestmentQueryService;

// TODO: secure request to "/investments" to be authenticated with Spring Security
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(produces = "application/json")
public class InvestmentController {

  private final InvestmentApplicationService applicationService;
  private final InvestmentQueryService investmentQueryService;

  public InvestmentController(InvestmentApplicationService applicationService,
      InvestmentQueryService investmentQueryService) {
    this.applicationService = applicationService;
    this.investmentQueryService = investmentQueryService;
  }

  @GetMapping("/fundraisings")
  public ResponseEntity<List<FundraisingData>> getOpenedFundraisings() {
    return ResponseEntity.ok(investmentQueryService.getOpenedFundraisings());
  }

  @GetMapping("/fundraisings/{fundraising_id}")
  public ResponseEntity<FundraisingData> getOpenedFundraisings(@PathVariable("fundraising_id") UUID fundraisingId) {
    return ResponseEntity.ok(investmentQueryService.getFundraisingDetail(fundraisingId));
  }

  @GetMapping("/investments")
  public ResponseEntity<List<InvestmentData>> getOwnedInvestments(Authentication auth) {
    UserId userId = getAuthenticatedUserId(auth);
    return ResponseEntity.ok(investmentQueryService.getOwnedInvestments(userId.getValue()));
  }

  @GetMapping("/investments/{investment_id}")
  public ResponseEntity<InvestmentData> getOwnedInvestmentDetails(
      @PathVariable("investment_id") UUID investmentId, Authentication auth) {
    UserId userId = getAuthenticatedUserId(auth);
    return ResponseEntity.ok(investmentQueryService.getOwnedInvestmentDetails(userId.getValue(), investmentId));
  }

  @PostMapping("/investments")
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
