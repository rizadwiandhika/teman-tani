package com.temantani.land.service.domain.ports.output.repository;

import java.util.List;
import java.util.Optional;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.land.service.domain.outbox.model.LandRegistetedOutboxMessage;

public interface LandOutboxRepository {

  Optional<List<LandRegistetedOutboxMessage>> findByOutboxStatuses(OutboxStatus... status);

  LandRegistetedOutboxMessage save(LandRegistetedOutboxMessage outbox);

  void deleteByOutboxStatus(OutboxStatus status);
}
