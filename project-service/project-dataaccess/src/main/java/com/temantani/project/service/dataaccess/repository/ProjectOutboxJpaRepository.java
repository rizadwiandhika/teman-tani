package com.temantani.project.service.dataaccess.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.project.service.dataaccess.entity.ProjectStatusUpdatedOutboxMessageEntity;

@Repository
public interface ProjectOutboxJpaRepository extends JpaRepository<ProjectStatusUpdatedOutboxMessageEntity, UUID> {

  Optional<List<ProjectStatusUpdatedOutboxMessageEntity>> findByOutboxStatusIn(List<OutboxStatus> status);

  void deleteByOutboxStatus(OutboxStatus status);

}
