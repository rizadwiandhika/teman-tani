package com.temantani.project.service.messaging.listener;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.temantani.kafka.KafkaConsumer;
import com.temantani.kafka.user.avro.model.UserAvroModel;
import com.temantani.project.service.domain.exception.DataAlreadyExistsException;
import com.temantani.project.service.domain.exception.SameBankAccountException;
import com.temantani.project.service.domain.ports.input.message.listener.UserMessageListener;
import com.temantani.project.service.messaging.mapper.ProjectMessagingDataMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaUserListener implements KafkaConsumer<UserAvroModel> {

  private final ProjectMessagingDataMapper mapper;
  private final UserMessageListener listener;

  public KafkaUserListener(ProjectMessagingDataMapper mapper, UserMessageListener listener) {
    this.mapper = mapper;
    this.listener = listener;
  }

  @Override
  @KafkaListener(id = "${kafka-consumer-config.user-consumer-group-id}", topics = "${project-service.user-topic-name}")
  public void recieve(
      @Payload List<UserAvroModel> messages,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
    messages.forEach(this::handleMessage);
  }

  private void handleMessage(UserAvroModel message) {
    if (message.getType().equals("ROLE_ACTIVATED")) {
      try {
        if (message.getActivatedRole().equals("ADMIN_PROJECT")) {
          listener.createManager(mapper.userAvroModelToManagerRegisteredMessage(message));
        }

        if (message.getActivatedRole().equals("LANDOWNER") || message.getActivatedRole().equals("INVESTOR")) {
          listener.createReceiver(mapper.userAvroModelToReceiverRegisteredMessage(message));
        }

      } catch (DataAlreadyExistsException e) {
        // Do nothing since it is a duplicate message
        log.warn("({}) {} is already exists. Ignoring message", message.getUserId(), message.getActivatedRole());
      }
      return;
    }

    if (message.getType().equals("PROFILE_UPDATED")) {
      try {
        listener.updateReceiverBank(mapper.userAvroModelToReceiverProfileUpdatedMessage(message));
      } catch (SameBankAccountException e) {
        log.warn("Ignoring message since bank account is the same as the old one");
      }
      return;
    }

    log.warn("Ignoring user message for: ({}) {}", message.getActivatedRole(), message.getUserId());

  }

}
