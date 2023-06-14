package com.temantani.project.service.dataaccess.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.project.service.dataaccess.entity.OutboxEntity;
import com.temantani.project.service.dataaccess.entity.type.OutboxType;

@Repository
public interface OutboxJpaRepository
    extends JpaRepository<OutboxEntity, UUID> {

  Optional<List<OutboxEntity>> findByTypeAndOutboxStatusIn(OutboxType type, List<OutboxStatus> status);

  void deleteByTypeAndOutboxStatus(OutboxType type, OutboxStatus status);

}
