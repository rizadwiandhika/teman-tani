package com.temantani.user.service.dataaccess.outbox.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.user.service.dataaccess.outbox.entity.UserOutboxMessageEntity;

@Repository
public interface UserOutboxJpaRepository extends JpaRepository<UserOutboxMessageEntity, UUID> {

  Optional<List<UserOutboxMessageEntity>> findByOutboxStatusIn(List<OutboxStatus> status);

  void deleteByOutboxStatus(OutboxStatus status);
}
