package com.temantani.investment.service.domain;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.investment.service.domain.dto.common.InvestmentBasicResponse;
import com.temantani.investment.service.domain.dto.create.CreateInvestmentRequest;
import com.temantani.investment.service.domain.entity.Investment;
import com.temantani.investment.service.domain.entity.Investor;
import com.temantani.investment.service.domain.entity.Project;
import com.temantani.investment.service.domain.event.InvestmentCreatedEvent;
import com.temantani.investment.service.domain.exception.InvestmentDomainException;
import com.temantani.investment.service.domain.mapper.InvestmentDataMapper;
import com.temantani.investment.service.domain.outbox.scheduler.investmentpaid.InvestmentPaidOutboxHelper;
import com.temantani.investment.service.domain.ports.input.service.InvestmentApplicationService;
import com.temantani.investment.service.domain.ports.output.payment.PaymentService;
import com.temantani.investment.service.domain.ports.output.repository.InvestmentRepository;
import com.temantani.investment.service.domain.ports.output.repository.InvestorRepository;
import com.temantani.investment.service.domain.ports.output.repository.ProjectRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InvestmentApplicationServiceImpl implements InvestmentApplicationService {

  private final InvestmentRepository investmentRepository;
  private final InvestorRepository investorRepository;
  private final ProjectRepository projectRepository;
  private final InvestmentDomainService domainService;
  private final PaymentService paymentService;

  public InvestmentApplicationServiceImpl(InvestmentRepository investmentRepository,
      InvestorRepository investorRepository, ProjectRepository projectRepository, InvestmentDataMapper mapper,
      InvestmentDomainService domainService, PaymentService paymentService, InvestmentPaidOutboxHelper helper) {
    this.investmentRepository = investmentRepository;
    this.investorRepository = investorRepository;
    this.projectRepository = projectRepository;
    this.domainService = domainService;
    this.paymentService = paymentService;
  }

  @Override
  @Transactional
  public InvestmentBasicResponse cancelInvestment(InvestmentId investmentId, List<String> reasons) {
    Project project = projectRepository.findByInvestmentId(investmentId)
        .orElseThrow(
            () -> new InvestmentDomainException("Project not found for investment: " + investmentId.getValue()));

    log.info("Canceling investment: {}", investmentId.getValue());

    domainService.cancelInvestment(project, investmentId, reasons);

    log.info("Investment canceled for: {}", investmentId.getValue());

    projectRepository.save(project);

    return InvestmentBasicResponse.builder()
        .investmentId(investmentId.getValue().toString())
        .message("Investment canceled successfully")
        .build();
  }

  @Override
  @Transactional
  public InvestmentBasicResponse createInvestment(CreateInvestmentRequest request) {
    Project project = findProjectByIdOrThrow(new ProjectId(UUID.fromString(request.getProjectId())));
    Investor investor = findInvestorByIdOrThrow(request.getInvestorId());

    InvestmentCreatedEvent event = domainService.validateAndInitiateInvestment(project, investor,
        new Money(request.getAmount()));

    projectRepository.save(project);

    return InvestmentBasicResponse.builder()
        .investmentId(event.getInvestment().getId().getValue().toString())
        .message("Investment created successfully")
        .build();
  }

  @Override
  @Transactional(readOnly = true)
  public Map<String, Object> initiatePayment(InvestmentId investmentId, Map<String, Object> paymentData) {
    Investment investment = findInvestmentByIdOrThrow(investmentId);

    log.info("Initiating payment for investment: {}", investment.getId().getValue().toString());
    return paymentService.pay(investment, paymentData);
  }

  @Override
  @Transactional
  public InvestmentBasicResponse payInvestment(InvestmentId investmentId) {
    Project project = projectRepository.findByInvestmentId(investmentId)
        .orElseThrow(() -> new InvestmentDomainException("Investment not found for: " + investmentId.getValue()));

    log.info("Paying investment: {}", investmentId.getValue());
    domainService.payInvestment(project, investmentId);

    projectRepository.save(project);

    return InvestmentBasicResponse.builder()
        .investmentId(investmentId.getValue().toString())
        .message("Investment paid successfully")
        .build();
  }

  private Investment findInvestmentByIdOrThrow(InvestmentId investmentId) {
    return investmentRepository.findById(investmentId)
        .orElseThrow(
            () -> new InvestmentDomainException("Investment not found id: " + investmentId.getValue().toString()));
  }

  private Investor findInvestorByIdOrThrow(UserId investorId) {
    return investorRepository.findById(investorId)
        .orElseThrow(() -> new InvestmentDomainException("Investor not found id: " + investorId.getValue().toString()));
  }

  private Project findProjectByIdOrThrow(ProjectId projectId) {
    return projectRepository.findById(projectId)
        .orElseThrow(() -> new InvestmentDomainException("Project not found id: " + projectId.getValue().toString()));
  }

}
