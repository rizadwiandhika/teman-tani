package com.temantani.investment.service.domain.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FundraisingClosureScheduler {

  private final FundraisingClosureHelper helper;

  public FundraisingClosureScheduler(FundraisingClosureHelper helper) {
    this.helper = helper;
  }

  @Scheduled(initialDelayString = "${investment-service.fundraising-closure-scheduler-initial-delay}", fixedDelayString = "${investment-service.fundraising-closure-scheduler-fixed-delay}")
  public void closeFundraisingScheduler() {
    // helper.cancelExpiredInvestments();
    helper.closeForAllClosingFundraising();
  }

}
