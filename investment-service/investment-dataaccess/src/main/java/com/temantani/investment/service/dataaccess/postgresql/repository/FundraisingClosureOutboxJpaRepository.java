package com.temantani.investment.service.dataaccess.postgresql.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.investment.service.dataaccess.postgresql.entity.FundraisingClosureOutboxEntity;

public interface FundraisingClosureOutboxJpaRepository extends JpaRepository<FundraisingClosureOutboxEntity, UUID> {

  Optional<List<FundraisingClosureOutboxEntity>> findByOutboxStatusIn(List<OutboxStatus> status);

  void deleteByOutboxStatus(OutboxStatus status);

}
