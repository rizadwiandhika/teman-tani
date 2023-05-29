package com.temantani.land.service.dataaccess.outbox.mapper;

import org.springframework.stereotype.Component;

import com.temantani.land.service.dataaccess.outbox.entity.LandOutboxEntity;
import com.temantani.land.service.domain.outbox.model.LandRegistetedOutboxMessage;

@Component
public class LandOutboxDataAccessMapper {

  public LandRegistetedOutboxMessage landOutboxEntityToLandRegistetedOutboxMessage(LandOutboxEntity entity) {
    return LandRegistetedOutboxMessage.builder()
        .id(entity.getId())
        .payload(entity.getPayload())
        .version(entity.getVersion())
        .outboxStatus(entity.getOutboxStatus())
        .createdAt(entity.getCreatedAt())
        .processedAt(entity.getProcessedAt())
        .build();
  }

  public LandOutboxEntity landRegistetedOutboxMessageToLandOutboxEntity(LandRegistetedOutboxMessage message) {
    return LandOutboxEntity.builder()
        .id(message.getId())
        .payload(message.getPayload())
        .version(message.getVersion())
        .outboxStatus(message.getOutboxStatus())
        .createdAt(message.getCreatedAt())
        .processedAt(message.getProcessedAt())
        .build();
  }

}
