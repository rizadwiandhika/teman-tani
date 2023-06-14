package com.temantani.project.service.dataaccess.adapter;

import static com.temantani.project.service.dataaccess.entity.type.OutboxType.FUNDRAISING_REGISTERED;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.project.service.dataaccess.mapper.ProjectDataAccessMapper;
import com.temantani.project.service.dataaccess.repository.OutboxJpaRepository;
import com.temantani.project.service.domain.outbox.model.fundraisingregistered.FundraisingRegisteredOutboxMessage;
import com.temantani.project.service.domain.ports.output.repository.FundraisingRegisteredOutboxRepository;

@Component
public class FundraisingRegisteredOutboxRepositoryImpl implements FundraisingRegisteredOutboxRepository {

  private final ProjectDataAccessMapper mapper;
  private final OutboxJpaRepository repo;

  public FundraisingRegisteredOutboxRepositoryImpl(ProjectDataAccessMapper mapper,
      OutboxJpaRepository repo) {
    this.mapper = mapper;
    this.repo = repo;
  }

  @Override
  public void deleteByOutboxStatus(OutboxStatus status) {
    repo.deleteByTypeAndOutboxStatus(FUNDRAISING_REGISTERED, status);
  }

  @Override
  public Optional<List<FundraisingRegisteredOutboxMessage>> findByOutboxStatuses(OutboxStatus... status) {
    return repo
        .findByTypeAndOutboxStatusIn(FUNDRAISING_REGISTERED, List.of(status))
        .map(list -> list.stream()
            .map(mapper::fundraisingRegisteredOutboxEntityToFundraisingRegisteredOutboxMessage)
            .collect(Collectors.toList()));
  }

  @Override
  public FundraisingRegisteredOutboxMessage save(FundraisingRegisteredOutboxMessage outbox) {
    return mapper.fundraisingRegisteredOutboxEntityToFundraisingRegisteredOutboxMessage(
        repo.save(mapper.fundraisingRegisteredOutboxMessageToFundraisingRegisteredOutboxEntity(outbox)));
  }

}
