package com.temantani.project.service.domain.outbox.scheduler.closefundraisingrequested;

import static com.temantani.domain.outbox.OutboxStatus.FAILED;
import static com.temantani.domain.outbox.OutboxStatus.STARTED;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.temantani.domain.outbox.OutboxScheduler;
import com.temantani.project.service.domain.outbox.model.closefundraisingrequested.CloseFundraisingRequestedOutboxMessage;
import com.temantani.project.service.domain.ports.output.publisher.CloseFundraisingRequestedEventPublisher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CloseFundraisingRequestedScheduler implements OutboxScheduler {

  private final CloseFundraisingRequestedOutboxHelper helper;
  private final CloseFundraisingRequestedEventPublisher publisher;

  public CloseFundraisingRequestedScheduler(CloseFundraisingRequestedOutboxHelper helper,
      CloseFundraisingRequestedEventPublisher publisher) {
    this.helper = helper;
    this.publisher = publisher;
  }

  @Override
  @Scheduled(initialDelayString = "${project-service.outbox-scheduler-initial-delay}", fixedDelayString = "${project-service.outbox-scheduler-fixed-delay}")
  public void processOutbox() {
    helper.getOutbox(STARTED, FAILED).forEach(this::publish);
  }

  private void publish(CloseFundraisingRequestedOutboxMessage outbox) {
    try {
      publisher.publish(outbox, helper::updateOutboxStatus);
    } catch (Exception e) {
      log.error("Unable to publish CloseFundraisingRequested: {}", outbox.getId(), e);
    }
  }

}
