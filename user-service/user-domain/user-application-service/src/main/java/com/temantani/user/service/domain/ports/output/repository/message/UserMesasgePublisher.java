package com.temantani.user.service.domain.ports.output.repository.message;

import java.util.function.BiConsumer;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.user.service.domain.outbox.model.UserOutboxMessage;

public interface UserMesasgePublisher {

  void publish(UserOutboxMessage message, BiConsumer<UserOutboxMessage, OutboxStatus> callback);

}
