package com.temantani.project.service.domain.outbox.scheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.temantani.domain.outbox.OutboxScheduler;
import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.project.service.domain.outbox.model.ProjectStatusUpdatedOutboxMessage;
import com.temantani.project.service.domain.ports.output.publisher.ProjectStatusUpdatedEventPublisher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProjectStatusUpdatedOutboxScheduler implements OutboxScheduler {

  private final ProjectStatusUpdatedEventPublisher publisher;
  private final ProjectStatusUpdatedOutboxHelper projectStatusUpdatedOutboxHelper;

  public ProjectStatusUpdatedOutboxScheduler(ProjectStatusUpdatedEventPublisher publisher,
      ProjectStatusUpdatedOutboxHelper projectStatusUpdatedOutboxHelper) {
    this.publisher = publisher;
    this.projectStatusUpdatedOutboxHelper = projectStatusUpdatedOutboxHelper;
  }

  @Override
  @Scheduled(initialDelayString = "${project-service.outbox-scheduler-initial-delay}", fixedDelayString = "${project-service.outbox-scheduler-fixed-delay}")
  public void processOutbox() {
    List<ProjectStatusUpdatedOutboxMessage> outboxMesages = projectStatusUpdatedOutboxHelper
        .getProjectStatusUpdatedOutbox(OutboxStatus.STARTED, OutboxStatus.FAILED);

    if (outboxMesages.isEmpty()) {
      return;
    }

    outboxMesages.forEach(outboxMessage -> {
      publisher.publish(outboxMessage, projectStatusUpdatedOutboxHelper::updateOutboxStatus);
    });

    log.info("Publishing {} project status updated outbox messages", outboxMesages.size());
  }

}
