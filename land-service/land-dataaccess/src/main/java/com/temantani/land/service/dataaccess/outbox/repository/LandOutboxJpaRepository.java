package com.temantani.land.service.dataaccess.outbox.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.land.service.dataaccess.outbox.entity.LandOutboxEntity;

@Repository
public interface LandOutboxJpaRepository extends JpaRepository<LandOutboxEntity, UUID> {

  Optional<List<LandOutboxEntity>> findByOutboxStatusIn(List<OutboxStatus> outboxStatuses);

  void deleteByOutboxStatus(OutboxStatus status);

}
