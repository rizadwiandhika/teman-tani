package com.temantani.project.service.domain.ports.output.publisher;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.domain.ports.publisher.EventPublisher;
import com.temantani.project.service.domain.outbox.model.ProjectStatusUpdatedOutboxMessage;

public interface ProjectStatusUpdatedEventPublisher
    extends EventPublisher<ProjectStatusUpdatedOutboxMessage, OutboxStatus> {

  // void publish(ProjectStatusUpdatedOutboxMessage message,
  // BiConsumer<ProjectStatusUpdatedOutboxMessage, OutboxStatus> callback);

}
