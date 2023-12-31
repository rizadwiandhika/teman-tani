package com.temantani.investment.service.domain.outbox.scheduler.investmentpaid;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.temantani.domain.outbox.OutboxScheduler;
import com.temantani.investment.service.domain.outbox.model.investmentpaid.InvestmentPaidOutboxMessage;
import com.temantani.investment.service.domain.ports.output.publisher.InvestmentPaidEventPublisher;

@Component
public class InvestmentPaidOutboxScheduler implements OutboxScheduler {

  private final InvestmentPaidOutboxHelper helper;

  public InvestmentPaidOutboxScheduler(InvestmentPaidOutboxHelper helper, InvestmentPaidEventPublisher publisher) {
    this.helper = helper;
  }

  @Override
  // @Scheduled(initialDelayString =
  // "${investment-service.outbox-scheduler-initial-delay}", fixedDelayString =
  // "${investment-service.outbox-scheduler-fixed-delay}")
  @Scheduled(cron = "@hourly")
  public void processOutbox() {
    helper.getOutbox().forEach(this::publish);
  }

  private void publish(InvestmentPaidOutboxMessage outbox) {
    // publisher.publish(outbox, helper::updateOutbox);
  }

}
