package com.temantani.investment.service.domain.ports.input.service;

import java.util.List;
import java.util.UUID;

import com.temantani.domain.exception.DataNotFoundException;
import com.temantani.investment.service.domain.dto.query.FundraisingData;
import com.temantani.investment.service.domain.dto.query.InvestmentData;

public interface InvestmentQueryService {

  List<FundraisingData> getOpenedFundraisings();

  FundraisingData getFundraisingDetail(UUID fundraisingId) throws DataNotFoundException;

  List<InvestmentData> getOwnedInvestments(UUID investorId);

  InvestmentData getOwnedInvestmentDetails(UUID investorId, UUID investmentId) throws DataNotFoundException;

}
