package com.temantani.domain.outbox;

import java.util.List;
import java.util.Optional;

public interface OutboxRepository<T extends OutboxMessage> {

  Optional<List<T>> findByOutboxStatuses(OutboxStatus... status);

  T save(T outbox);

  void deleteByOutboxStatus(OutboxStatus status);
}
