package com.temantani.project.service.domain.ports.output.publisher;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.domain.ports.publisher.EventPublisher;
import com.temantani.project.service.domain.outbox.model.fundraisingregistered.FundraisingRegisteredOutboxMessage;

public interface FundraisingRegisteredEventPublisher
    extends EventPublisher<FundraisingRegisteredOutboxMessage, OutboxStatus> {

  // void publish(ProjectStatusUpdatedOutboxMessage message,
  // BiConsumer<ProjectStatusUpdatedOutboxMessage, OutboxStatus> callback);

}
