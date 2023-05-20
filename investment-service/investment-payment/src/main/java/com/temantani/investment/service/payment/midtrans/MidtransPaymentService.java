package com.temantani.investment.service.payment.midtrans;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.midtrans.Midtrans;
import com.midtrans.httpclient.CoreApi;
import com.midtrans.httpclient.error.MidtransError;
import com.temantani.investment.service.domain.entity.Investment;
import com.temantani.investment.service.domain.exception.InvestmentDomainException;
import com.temantani.investment.service.domain.ports.output.payment.PaymentService;
import com.temantani.investment.service.payment.midtrans.config.MidtransConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MidtransPaymentService implements PaymentService {

  private final MidtransConfig midtransConfig;

  public MidtransPaymentService(MidtransConfig midtransConfig) {
    this.midtransConfig = midtransConfig;
  }

  @Override
  public Map<String, Object> pay(Investment investment, Map<String, Object> paymentData) {
    JSONObject result;

    try {
      result = CoreApi.chargeTransaction(requestBody(investment, paymentData));
    } catch (MidtransError e) {
      throw new InvestmentDomainException(e.getMessage(), e);
    }

    return result.toMap();
  }

  @Override
  public Boolean validate(Map<String, Object> paymentData) throws InvestmentDomainException {
    String signatureKey = paymentData.getOrDefault("signature_key", "").toString();

    String orderId = paymentData.getOrDefault("order_id", "").toString();
    String statusCode = paymentData.getOrDefault("status_code", "").toString();
    String grossAmount = paymentData.getOrDefault("gross_amount", "").toString();
    String serverKey = Midtrans.serverKey;

    if (signatureKey.isEmpty() || orderId.isEmpty() || statusCode.isEmpty() || grossAmount.isEmpty()) {
      return false;
    }

    String hash;
    try {
      hash = hashSHA512(orderId + statusCode + grossAmount + serverKey);
    } catch (Exception e) {
      throw new InvestmentDomainException(e.getMessage(), e);
    }

    return hash.equals(signatureKey);
  }

  private Map<String, Object> requestBody(Investment investment, Map<String, Object> paymentData) {
    Map<String, Object> params = new HashMap<>();
    Map<String, String> transactionDetails = new HashMap<>();
    Map<String, String> creditCard = new HashMap<>();
    Object token = paymentData.getOrDefault("token_id", "");

    transactionDetails.put("order_id", investment.getId().getValue().toString());
    transactionDetails.put("gross_amount", investment.getAmount().getAmount().intValue() + "");

    creditCard.put("authentication", "true");
    if (token instanceof String) {
      creditCard.put("token_id", (String) token);
    }

    params.put("transaction_details", transactionDetails);
    params.put("credit_card", creditCard);
    params.put("payment_type", "credit_card");

    return params;
  }

  private String hashSHA512(String input) throws NoSuchAlgorithmException {
    // Create SHA-512 instance
    MessageDigest digest = MessageDigest.getInstance("SHA-512");

    // Convert input string to bytes
    byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

    // Convert byte array to hexadecimal string
    StringBuilder hexString = new StringBuilder();
    for (byte b : encodedHash) {
      String hex = Integer.toHexString(0xff & b);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }

    return hexString.toString();
  }

  @PostConstruct
  public void init() {
    Midtrans.serverKey = midtransConfig.getServerKey();
    Midtrans.clientKey = midtransConfig.getClientKey();
    Midtrans.isProduction = midtransConfig.getIsProduction();

    log.info("Midtrans payment service initialized");
  }

}
