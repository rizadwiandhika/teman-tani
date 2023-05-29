package com.temantani.land.service.domain.outbox.scheduler;

import static com.temantani.domain.outbox.OutboxStatus.FAILED;
import static com.temantani.domain.outbox.OutboxStatus.STARTED;

import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.domain.outbox.OutboxScheduler;
import com.temantani.land.service.domain.outbox.model.LandRegistetedOutboxMessage;
import com.temantani.land.service.domain.ports.output.message.LandRegisteredMessagePublisher;

@Component
public class LandOutboxScheduler implements OutboxScheduler {

  private final LandOutboxHelper landOutboxHelper;
  private final LandRegisteredMessagePublisher publisher;

  public LandOutboxScheduler(LandOutboxHelper landOutboxHelper, LandRegisteredMessagePublisher publisher) {
    this.landOutboxHelper = landOutboxHelper;
    this.publisher = publisher;
  }

  @Override
  @Transactional
  @Scheduled(initialDelayString = "${land-service.outbox-scheduler-initial-delay}", fixedDelayString = "${land-service.outbox-scheduler-fixed-delay}")
  public void processOutbox() {
    Optional<List<LandRegistetedOutboxMessage>> outboxMessages = landOutboxHelper.getOutboxMessages(STARTED, FAILED);
    if (outboxMessages.isPresent() && outboxMessages.get().size() > 0) {
      outboxMessages.get().forEach(this::publish);
    }
  }

  private void publish(LandRegistetedOutboxMessage landregistetedoutboxmessage1) {
    publisher.publish(landregistetedoutboxmessage1, landOutboxHelper::updateOutboxMessage);
  }

}
