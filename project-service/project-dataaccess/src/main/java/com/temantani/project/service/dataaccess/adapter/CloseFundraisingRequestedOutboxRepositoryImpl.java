package com.temantani.project.service.dataaccess.adapter;

import static com.temantani.project.service.dataaccess.entity.type.OutboxType.CLOSE_FUNDRAISING_REQUESTED;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.project.service.dataaccess.mapper.ProjectDataAccessMapper;
import com.temantani.project.service.dataaccess.repository.OutboxJpaRepository;
import com.temantani.project.service.domain.outbox.model.closefundraisingrequested.CloseFundraisingRequestedOutboxMessage;
import com.temantani.project.service.domain.ports.output.repository.CloseFundraisingRequestedOutboxRepository;

@Repository
public class CloseFundraisingRequestedOutboxRepositoryImpl implements CloseFundraisingRequestedOutboxRepository {
  private final ProjectDataAccessMapper mapper;
  private final OutboxJpaRepository repo;

  public CloseFundraisingRequestedOutboxRepositoryImpl(ProjectDataAccessMapper mapper,
      OutboxJpaRepository repo) {
    this.mapper = mapper;
    this.repo = repo;
  }

  @Override
  public void deleteByOutboxStatus(OutboxStatus status) {
    repo.deleteByTypeAndOutboxStatus(CLOSE_FUNDRAISING_REQUESTED, status);
  }

  @Override
  public Optional<List<CloseFundraisingRequestedOutboxMessage>> findByOutboxStatuses(OutboxStatus... status) {
    return repo
        .findByTypeAndOutboxStatusIn(CLOSE_FUNDRAISING_REQUESTED, List.of(status))
        .map(list -> list.stream()
            .map(mapper::closeFundraisingRequestedOutboxEntityToCloseFundraisingRqeustedOutboxMessage)
            .collect(Collectors.toList()));
  }

  @Override
  public CloseFundraisingRequestedOutboxMessage save(CloseFundraisingRequestedOutboxMessage outbox) {
    return mapper.closeFundraisingRequestedOutboxEntityToCloseFundraisingRqeustedOutboxMessage(
        repo.save(mapper.closeFundraisingRequestedOutboxMessageToCloseFundraisingRequestedOutboxEntity(outbox)));
  }

}
