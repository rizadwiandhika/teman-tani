package com.temantani.investment.service.dataaccess.postgresql.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.investment.service.dataaccess.postgresql.mapper.InvestmentDataAccessMapper;
import com.temantani.investment.service.dataaccess.postgresql.repository.FundraisingClosureOutboxJpaRepository;
import com.temantani.investment.service.domain.outbox.model.fundraisingclosure.FundraisingClosureOutboxMessage;
import com.temantani.investment.service.domain.ports.output.repository.FundraisingClosureOutboxRepository;

@Repository
public class FundraisingClosureOutboxRepositoryImpl implements FundraisingClosureOutboxRepository {

  private final FundraisingClosureOutboxJpaRepository repo;
  private final InvestmentDataAccessMapper mapper;

  public FundraisingClosureOutboxRepositoryImpl(FundraisingClosureOutboxJpaRepository repo,
      InvestmentDataAccessMapper mapper) {
    this.repo = repo;
    this.mapper = mapper;
  }

  @Override
  public void deleteByOutboxStatus(OutboxStatus status) {
    repo.deleteByOutboxStatus(status);
  }

  @Override
  public Optional<List<FundraisingClosureOutboxMessage>> findByOutboxStatuses(OutboxStatus... status) {
    return repo.findByOutboxStatusIn(List.of(status))
        .map(list -> list.stream()
            .map(mapper::fundraisingClosureOutboxEntityToFundraisingClosureOutboxMessage)
            .collect(Collectors.toList()));
  }

  @Override
  public FundraisingClosureOutboxMessage save(FundraisingClosureOutboxMessage outbox) {
    return mapper.fundraisingClosureOutboxEntityToFundraisingClosureOutboxMessage(
        repo.save(mapper.fundraisingClosureOutboxMessageToFundraisingClosureOutboxEntity(outbox)));
  }

}
