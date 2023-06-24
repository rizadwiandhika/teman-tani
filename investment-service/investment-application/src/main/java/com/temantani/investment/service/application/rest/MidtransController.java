package com.temantani.investment.service.application.rest;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.investment.service.application.dto.MidtransNotificationDTO;
import com.temantani.investment.service.domain.exception.InvestmentDomainException;
import com.temantani.investment.service.domain.exception.InvestmentPaymentException;
import com.temantani.investment.service.domain.ports.input.service.InvestmentApplicationService;
import com.temantani.investment.service.domain.ports.output.payment.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/midtrans")
public class MidtransController {
  private final InvestmentApplicationService applicationService;
  private final PaymentService paymentService;
  private final ObjectMapper objectMapper;

  public MidtransController(InvestmentApplicationService applicationService, PaymentService paymentService,
      ObjectMapper objectMapper, @Value("${server.port}") String serverPort) {
    this.applicationService = applicationService;
    this.paymentService = paymentService;
    this.objectMapper = objectMapper;
  }

  @PostMapping("/investments/{investment_id}/charge")
  public ResponseEntity<Map<String, Object>> payInvestment(
      @RequestBody Map<String, Object> request,
      @PathVariable("investment_id") UUID investmentId) {
    return ResponseEntity.ok(applicationService.initiatePayment(new InvestmentId(investmentId), request));
  }

  @PostMapping(value = "/notification")
  public ResponseEntity<?> notificationHandler(@RequestBody MidtransNotificationDTO payload) {
    log.info("[MidtransController] notificationHandler: {}", payload.toString());
    InvestmentId investmentId = new InvestmentId(UUID.fromString(payload.getOrderId()));

    @SuppressWarnings("unchecked")
    Map<String, Object> payloadMap = objectMapper.convertValue(payload, Map.class);
    if (paymentService.validate(payloadMap) == false) {
      throw new InvestmentDomainException("Payment not valid!!");
    }

    if (isTransactionSuccess(payload)) {
      log.info("[MidtransController] notificationHandler: transaction success");
      try {
        applicationService.payInvestment(investmentId);
      } catch (InvestmentPaymentException e) {
      }
      return ResponseEntity.ok().build();
    }

    if (isTransactionPending(payload.getTransactionStatus())) {
      log.warn("[MidtransController] notificationHandler: transaction pending");
      return ResponseEntity.ok().build();
    }

    log.warn("[MidtransController] notificationHandler: transaction failed");
    applicationService.cancelInvestment(investmentId,
        Arrays.asList(payload.getStatusMessage(), payload.getTransactionStatus()));

    return ResponseEntity.ok().build();
  }

  @GetMapping(value = "/investments/{investment_id}/{status}")
  public ResponseEntity<String> redirectView(@PathVariable("investment_id") String investmentId,
      @PathVariable("status") String status) {
    String message = String.format("Payment for investment: '%s' is %s. We will process it shortly", investmentId,
        status);
    return ResponseEntity.ok(message);
  }

  @PostMapping(value = "/redirect/ui", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public RedirectView redirectUI(@RequestParam("response") String payload,
      @RequestParam(value = "investmentId", defaultValue = "no-id") String investmentId) throws IOException {
    String decodedData = URLDecoder.decode(payload, "UTF-8");
    MidtransNotificationDTO dto = objectMapper.readValue(decodedData, MidtransNotificationDTO.class);
    log.info("DTO: {}", dto);

    // if (isTransactionSuccess(dto)) {
    // return new RedirectView(serverHost + "/midtrans/investments/" +
    // dto.getOrderId() + "/success");
    // }

    // if (isTransactionPending(dto.getTransactionStatus())) {
    // return new RedirectView(serverHost + "/midtrans/investments/" +
    // dto.getOrderId() + "/pending");
    // }

    // return new RedirectView(serverHost + "/midtrans/investments/" +
    // dto.getOrderId() + "/failed");

    return new RedirectView(String.format("http://127.0.0.1:3000/investments/%s", investmentId));
  }

  private boolean isTransactionPending(String transactionStatus) {
    return transactionStatus.equals("pending");
  }

  private boolean isTransactionSuccess(MidtransNotificationDTO dto) {
    return dto.getStatusCode().equals("200") && dto.getTransactionStatus().equals("capture")
        && dto.getFraudStatus().equals("accept");
  }
}
