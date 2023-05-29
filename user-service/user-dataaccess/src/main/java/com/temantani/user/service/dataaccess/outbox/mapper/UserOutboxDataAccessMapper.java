package com.temantani.user.service.dataaccess.outbox.mapper;

import org.springframework.stereotype.Component;

import com.temantani.user.service.dataaccess.outbox.entity.UserOutboxMessageEntity;
import com.temantani.user.service.domain.outbox.model.UserOutboxMessage;

@Component
public class UserOutboxDataAccessMapper {

  public UserOutboxMessageEntity userOutboxMessageTouserOutboxMessageEntity(UserOutboxMessage outbox) {
    return UserOutboxMessageEntity.builder()
        .id(outbox.getId())
        .outboxStatus(outbox.getOutboxStatus())
        .version(outbox.getVersion())
        .payload(outbox.getPayload())
        .createdAt(outbox.getCreatedAt())
        .processedAt(outbox.getProcessedAt())
        .build();
  }

  public UserOutboxMessage userOutboxMessageEntityToUserOutboxMessage(UserOutboxMessageEntity entity) {
    return UserOutboxMessage.builder()
        .id(entity.getId())
        .outboxStatus(entity.getOutboxStatus())
        .version(entity.getVersion())
        .payload(entity.getPayload())
        .createdAt(entity.getCreatedAt())
        .processedAt(entity.getProcessedAt())
        .build();
  }

}
