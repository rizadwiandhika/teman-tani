package com.temantani.user.service.domain.outbox.scheduler;

import static com.temantani.domain.outbox.OutboxStatus.COMPLETED;

import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.temantani.domain.outbox.OutboxScheduler;
import com.temantani.user.service.domain.outbox.model.UserOutboxMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserOutboxCleanerScheduler implements OutboxScheduler {

  private final UserOutboxHelper helper;

  public UserOutboxCleanerScheduler(UserOutboxHelper helper) {
    this.helper = helper;
  }

  @Override
  @Scheduled(cron = "@midnight")
  public void processOutbox() {
    log.info("Cleaning outbox");
    Optional<List<UserOutboxMessage>> results = helper.getUserOutboxMessageByStatuses(COMPLETED);
    if (results.isPresent() && results.get().size() > 0) {
      helper.deleteOutbxByStatus(COMPLETED);
      log.info("{} outbox messages deleted", results.get().size());
    }

  }

}
