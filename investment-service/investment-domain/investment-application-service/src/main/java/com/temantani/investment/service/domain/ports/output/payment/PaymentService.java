package com.temantani.investment.service.domain.ports.output.payment;

import java.util.Map;

import com.temantani.investment.service.domain.entity.Investment;
import com.temantani.investment.service.domain.exception.InvestmentDomainException;

public interface PaymentService {

  Map<String, Object> pay(Investment investment, Map<String, Object> paymentData);

  Boolean validate(Map<String, Object> paymentData) throws InvestmentDomainException;

}
