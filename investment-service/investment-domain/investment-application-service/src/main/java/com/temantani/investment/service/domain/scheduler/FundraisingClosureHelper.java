package com.temantani.investment.service.domain.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.investment.service.domain.InvestmentDomainService;
import com.temantani.investment.service.domain.entity.Investment;
import com.temantani.investment.service.domain.entity.Fundraising;
import com.temantani.investment.service.domain.event.ProjectClosureEvent;
import com.temantani.investment.service.domain.mapper.InvestmentDataMapper;
import com.temantani.investment.service.domain.outbox.scheduler.fundraisingclosure.FundraisingOutboxHelper;
import com.temantani.investment.service.domain.ports.output.repository.InvestmentRepository;
import com.temantani.investment.service.domain.ports.output.repository.FundraisingRepository;
import com.temantani.investment.service.domain.valueobject.InvestmentStatus;
import com.temantani.investment.service.domain.valueobject.FundraisingStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FundraisingClosureHelper {

  private final InvestmentDomainService domainService;
  private final FundraisingRepository projectRepository;
  private final InvestmentRepository investmentRepository;
  private final FundraisingOutboxHelper fundraisingOutboxHelper;
  private final InvestmentDataMapper mapper;

  public FundraisingClosureHelper(InvestmentDomainService domainService, FundraisingRepository projectRepository,
      InvestmentRepository investmentRepository, FundraisingOutboxHelper fundraisingOutboxHelper,
      InvestmentDataMapper mapper) {
    this.domainService = domainService;
    this.projectRepository = projectRepository;
    this.investmentRepository = investmentRepository;
    this.fundraisingOutboxHelper = fundraisingOutboxHelper;
    this.mapper = mapper;
  }

  @Transactional
  public void cancelExpiredInvestments() {
    List<Investment> investments = investmentRepository.findByStatus(InvestmentStatus.PENDING)
        .orElse(new ArrayList<>());

    log.info("Found: {} PENDING Investments", investments.size());
    if (investments.isEmpty()) {
      return;
    }

    investments.forEach(i -> {
      if (i.isExpired()) {
        i.cancel(List.of("Investment has been expired"));
      }
    });

    investmentRepository.saveAll(investments);
    log.info("{} Investments was cancelled due to expiration",
        investments.stream().filter(i -> i.getStatus() == InvestmentStatus.CANCELED).toList().size());
  }

  @Transactional
  public void closeForAllClosingFundraising() {
    Optional<List<Fundraising>> closingProjectsOp = projectRepository.findByStatus(FundraisingStatus.CLOSING);
    if (closingProjectsOp.isEmpty()) {
      return;
    }

    closingProjectsOp.get().stream().forEach(project -> {
      ProjectClosureEvent event = domainService.closeProjectForInvestment(project);

      projectRepository.save(event.getProject());
      fundraisingOutboxHelper.createOutbox(mapper.projectClosureEventToFundraisingClosureEventPayload(event));
    });
  }

}
