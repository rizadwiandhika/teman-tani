package com.temantani.project.service.dataaccess.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.project.service.dataaccess.mapper.ProjectDataAccessMapper;
import com.temantani.project.service.dataaccess.repository.ProjectOutboxJpaRepository;
import com.temantani.project.service.domain.outbox.model.ProjectStatusUpdatedOutboxMessage;
import com.temantani.project.service.domain.ports.output.repository.ProjectOutboxRepository;

@Repository
public class ProjectOutboxRepositoryImpl implements ProjectOutboxRepository {

  private final ProjectDataAccessMapper mapper;
  private final ProjectOutboxJpaRepository repo;

  public ProjectOutboxRepositoryImpl(ProjectDataAccessMapper mapper, ProjectOutboxJpaRepository repo) {
    this.mapper = mapper;
    this.repo = repo;
  }

  @Override
  public void deleteByOutboxStatus(OutboxStatus status) {
    repo.deleteByOutboxStatus(status);
  }

  @Override
  public Optional<List<ProjectStatusUpdatedOutboxMessage>> findByOutboxStatuses(OutboxStatus... status) {
    return repo
        .findByOutboxStatusIn(List.of(status))
        .map(list -> list.stream()
            .map(mapper::projectStatusUpdatedOutboxMessageEntityToProjectStatusUpdatedOutboxMessage)
            .collect(Collectors.toList()));

  }

  @Override
  public ProjectStatusUpdatedOutboxMessage save(ProjectStatusUpdatedOutboxMessage outbox) {
    return mapper.projectStatusUpdatedOutboxMessageEntityToProjectStatusUpdatedOutboxMessage(
        repo.save(mapper.projectStatusUpdatedOutboxMessageToProjectStatusUpdatedOutboxMessage(outbox)));
  }

}
