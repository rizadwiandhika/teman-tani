package com.temantani.land.service.domain.ports.output.message;

import java.util.function.BiConsumer;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.land.service.domain.outbox.model.LandRegistetedOutboxMessage;

public interface LandRegisteredMessagePublisher {

  void publish(LandRegistetedOutboxMessage outbox, BiConsumer<LandRegistetedOutboxMessage, OutboxStatus> callback);
}
