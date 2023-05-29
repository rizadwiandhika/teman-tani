package com.temantani.user.service.dataaccess.outbox.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.user.service.dataaccess.outbox.entity.UserOutboxMessageEntity;
import com.temantani.user.service.dataaccess.outbox.mapper.UserOutboxDataAccessMapper;
import com.temantani.user.service.dataaccess.outbox.repository.UserOutboxJpaRepository;
import com.temantani.user.service.domain.outbox.model.UserOutboxMessage;
import com.temantani.user.service.domain.ports.output.repository.UserOutboxRepository;

@Component
public class UserOutboxRepositoryImpl implements UserOutboxRepository {

  private final UserOutboxJpaRepository repository;
  private final UserOutboxDataAccessMapper mapper;

  public UserOutboxRepositoryImpl(UserOutboxJpaRepository repository, UserOutboxDataAccessMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public Optional<List<UserOutboxMessage>> findByOutboxStatuses(OutboxStatus... status) {
    Optional<List<UserOutboxMessageEntity>> results = repository.findByOutboxStatusIn(List.of(status));
    if (results.isEmpty()) {
      return Optional.empty();
    }

    List<UserOutboxMessage> data = results
        .get()
        .stream()
        .map(mapper::userOutboxMessageEntityToUserOutboxMessage)
        .collect(Collectors.toList());

    return Optional.of(data);
  }

  @Override
  public UserOutboxMessage save(UserOutboxMessage outbox) {
    return mapper.userOutboxMessageEntityToUserOutboxMessage(
        repository.save(mapper.userOutboxMessageTouserOutboxMessageEntity(outbox)));
  }

  @Override
  public void deleteByOutboxStatus(OutboxStatus status) {
    repository.deleteByOutboxStatus(status);
  }

}
