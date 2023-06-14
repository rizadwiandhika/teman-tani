package com.temantani.investment.service.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.domain.exception.DataNotFoundException;
import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.investment.service.domain.dto.query.FundraisingData;
import com.temantani.investment.service.domain.dto.query.InvestmentData;
import com.temantani.investment.service.domain.entity.Investment;
import com.temantani.investment.service.domain.mapper.InvestmentDataMapper;
import com.temantani.investment.service.domain.ports.input.service.InvestmentQueryService;
import com.temantani.investment.service.domain.ports.output.repository.InvestmentRepository;
import com.temantani.investment.service.domain.ports.output.repository.FundraisingRepository;
import com.temantani.investment.service.domain.valueobject.FundraisingStatus;

@Service
public class InvestmentQueryServiceImpl implements InvestmentQueryService {

  private final FundraisingRepository projectRepository;
  private final InvestmentRepository investmentRepository;
  private final InvestmentDataMapper mapper;

  public InvestmentQueryServiceImpl(FundraisingRepository projectRepository, InvestmentRepository investmentRepository,
      InvestmentDataMapper mapper) {
    this.projectRepository = projectRepository;
    this.investmentRepository = investmentRepository;
    this.mapper = mapper;
  }

  @Override
  @Transactional(readOnly = true)
  public FundraisingData getFundraisingDetail(UUID fundraisingId) throws DataNotFoundException {
    return projectRepository.findById(new ProjectId(fundraisingId))
        .map(mapper::projectToFundraisingData)
        .orElseThrow(() -> new DataNotFoundException("Fundraising: " + fundraisingId + " not found"));
  }

  @Override
  @Transactional(readOnly = true)
  public List<FundraisingData> getOpenedFundraisings() {
    return projectRepository.findByStatus(FundraisingStatus.OPEN)
        .map((projects) -> projects.stream().map(mapper::projectToFundraisingData).collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }

  @Override
  @Transactional(readOnly = true)
  public InvestmentData getOwnedInvestmentDetails(UUID investorId, UUID investmentId) throws DataNotFoundException {
    Investment investment = investmentRepository.findById(new InvestmentId(investmentId))
        .orElseThrow(() -> new DataNotFoundException("Investment not found: " + investmentId));

    if (investment.getInvestorId().equals(new UserId(investorId)) == false) {
      throw new DataNotFoundException("Investment not found: " + investmentId);
    }

    return mapper.investmentToInvestmentData(investment);
  }

  @Override
  @Transactional(readOnly = true)
  public List<InvestmentData> getOwnedInvestments(UUID investorId) {
    return investmentRepository.findByInvestorId(new UserId(investorId))
        .map((list) -> list.stream().map(mapper::investmentToInvestmentData).collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }

}
