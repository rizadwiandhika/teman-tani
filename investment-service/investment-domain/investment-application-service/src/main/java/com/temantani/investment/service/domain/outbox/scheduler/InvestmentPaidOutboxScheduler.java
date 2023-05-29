package com.temantani.investment.service.domain.outbox.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.temantani.domain.outbox.OutboxScheduler;
import com.temantani.investment.service.domain.outbox.model.InvestmentPaidOutboxMessage;
import com.temantani.investment.service.domain.ports.output.publisher.InvestmentPaidEventPublisher;

@Component
public class InvestmentPaidOutboxScheduler implements OutboxScheduler {

  private final InvestmentPaidOutboxHelper helper;
  private final InvestmentPaidEventPublisher publisher;

  public InvestmentPaidOutboxScheduler(InvestmentPaidOutboxHelper helper, InvestmentPaidEventPublisher publisher) {
    this.helper = helper;
    this.publisher = publisher;
  }

  @Override
  @Scheduled(initialDelayString = "${investment-service.outbox-scheduler-initial-delay}", fixedDelayString = "${investment-service.outbox-scheduler-fixed-delay}")
  public void processOutbox() {
    helper.getOutbox().forEach(this::publish);
  }

  private void publish(InvestmentPaidOutboxMessage outbox) {
    publisher.publish(outbox, helper::updateOutbox);
  }

}
