package com.temantani.investment.service.domain.outbox.scheduler.fundraisingclosure;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.temantani.domain.outbox.OutboxScheduler;
import com.temantani.investment.service.domain.outbox.model.fundraisingclosure.FundraisingClosureOutboxMessage;
import com.temantani.investment.service.domain.ports.output.publisher.FundraisingClosureEventPublisher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FundraisingClosureOutboxScheduler implements OutboxScheduler {

  private final FundraisingOutboxHelper helper;
  private final FundraisingClosureEventPublisher publisher;

  public FundraisingClosureOutboxScheduler(FundraisingOutboxHelper helper, FundraisingClosureEventPublisher publisher) {
    this.helper = helper;
    this.publisher = publisher;
  }

  @Override
  @Scheduled(initialDelayString = "${investment-service.outbox-scheduler-initial-delay}", fixedDelayString = "${investment-service.outbox-scheduler-fixed-delay}")
  public void processOutbox() {
    helper.getOutboxes().forEach(this::publish);
  }

  private void publish(FundraisingClosureOutboxMessage outbox) {
    try {
      publisher.publish(outbox, helper::updateOutboxStatus);
    } catch (Exception e) {
      log.error("Unable to publish Fundraising Closure Event: {}", outbox.getId(), e);
    }
  }

}
