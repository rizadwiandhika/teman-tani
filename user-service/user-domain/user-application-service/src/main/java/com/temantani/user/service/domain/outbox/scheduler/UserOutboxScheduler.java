package com.temantani.user.service.domain.outbox.scheduler;

import static com.temantani.domain.outbox.OutboxStatus.FAILED;
import static com.temantani.domain.outbox.OutboxStatus.STARTED;

import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.domain.outbox.OutboxScheduler;
import com.temantani.user.service.domain.outbox.model.UserOutboxMessage;
import com.temantani.user.service.domain.ports.output.repository.message.UserMesasgePublisher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserOutboxScheduler implements OutboxScheduler {

  private final UserOutboxHelper userOutboxHelper;
  private final UserMesasgePublisher userMesasgePublisher;

  public UserOutboxScheduler(UserOutboxHelper userOutboxHelper, UserMesasgePublisher userMesasgePublisher) {
    this.userOutboxHelper = userOutboxHelper;
    this.userMesasgePublisher = userMesasgePublisher;
  }

  @Override
  @Transactional
  @Scheduled(initialDelayString = "${user-service.outbox-scheduler-initial-delay}", fixedDelayString = "${user-service.outbox-scheduler-fixed-delay}")
  public void processOutbox() {
    Optional<List<UserOutboxMessage>> outboxes = userOutboxHelper.getUserOutboxMessageByStatuses(STARTED, FAILED);
    if (outboxes.isPresent() && outboxes.get().size() > 0) {
      outboxes.get().forEach(this::publishOutbox);
      log.info("{} outbox messages have been published", outboxes.get().size());
    }
  }

  private void publishOutbox(UserOutboxMessage outbox) {
    log.info("Publishing outbox message: {}", outbox);
    userMesasgePublisher.publish(outbox, userOutboxHelper::updateUserOutboxMessage);
  }

}
