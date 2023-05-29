package com.temantani.user.service.domain.ports.output.repository;

import java.util.List;
import java.util.Optional;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.user.service.domain.outbox.model.UserOutboxMessage;

public interface UserOutboxRepository {

  Optional<List<UserOutboxMessage>> findByOutboxStatuses(OutboxStatus... status);

  UserOutboxMessage save(UserOutboxMessage outbox);

  void deleteByOutboxStatus(OutboxStatus status);

}
