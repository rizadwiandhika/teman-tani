package com.temantani.land.service.dataaccess.outbox.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.land.service.dataaccess.outbox.entity.LandOutboxEntity;
import com.temantani.land.service.dataaccess.outbox.mapper.LandOutboxDataAccessMapper;
import com.temantani.land.service.dataaccess.outbox.repository.LandOutboxJpaRepository;
import com.temantani.land.service.domain.outbox.model.LandRegistetedOutboxMessage;
import com.temantani.land.service.domain.ports.output.repository.LandOutboxRepository;

@Component
public class LandOutboxRepositoryImpl implements LandOutboxRepository {

  private final LandOutboxJpaRepository repo;
  private final LandOutboxDataAccessMapper mapper;

  public LandOutboxRepositoryImpl(LandOutboxJpaRepository outboxJpaRepository,
      LandOutboxDataAccessMapper outboxDataAccessMapper) {
    this.repo = outboxJpaRepository;
    this.mapper = outboxDataAccessMapper;
  }

  @Override
  public Optional<List<LandRegistetedOutboxMessage>> findByOutboxStatuses(OutboxStatus... status) {
    Optional<List<LandOutboxEntity>> result = repo.findByOutboxStatusIn(List.of(status));
    if (result.isEmpty()) {
      return Optional.empty();
    }

    List<LandRegistetedOutboxMessage> data = result
        .get()
        .stream()
        .map(mapper::landOutboxEntityToLandRegistetedOutboxMessage)
        .collect(Collectors.toList());

    return Optional.of(data);
  }

  @Override
  public LandRegistetedOutboxMessage save(LandRegistetedOutboxMessage outbox) {
    return mapper.landOutboxEntityToLandRegistetedOutboxMessage(
        repo.save(mapper.landRegistetedOutboxMessageToLandOutboxEntity(outbox)));
  }

  @Override
  public void deleteByOutboxStatus(OutboxStatus status) {
    repo.deleteByOutboxStatus(status);
  }

}
