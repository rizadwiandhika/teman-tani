package com.temantani.investment.service.domain.ports.input.service;

import java.util.List;
import java.util.Map;

import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.investment.service.domain.dto.common.InvestmentBasicResponse;
import com.temantani.investment.service.domain.dto.create.CreateInvestmentRequest;

public interface InvestmentApplicationService {

  InvestmentBasicResponse createInvestment(CreateInvestmentRequest request);

  Map<String, Object> initiatePayment(InvestmentId investmentId, Map<String, Object> paymentData);

  InvestmentBasicResponse payInvestment(InvestmentId investmentId);

  InvestmentBasicResponse cancelInvestment(InvestmentId investmentId, List<String> reasons);

}