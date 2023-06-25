package com.temantani.project.service.messaging.listener;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.temantani.domain.exception.DataAlreadyExistsException;
import com.temantani.kafka.KafkaConsumer;
import com.temantani.kafka.producer.helper.KafkaMessageHelper;
import com.temantani.kafka.user.json.model.UserJsonModel;
import com.temantani.project.service.domain.exception.SameBankAccountException;
import com.temantani.project.service.domain.ports.input.message.listener.UserMessageListener;
import com.temantani.project.service.messaging.mapper.ProjectMessagingDataMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaUserListener implements KafkaConsumer<String> {

  private final ProjectMessagingDataMapper mapper;
  private final UserMessageListener listener;
  private final KafkaMessageHelper helper;

  public KafkaUserListener(ProjectMessagingDataMapper mapper, UserMessageListener listener, KafkaMessageHelper helper) {
    this.mapper = mapper;
    this.listener = listener;
    this.helper = helper;
  }

  @Override
  @KafkaListener(id = "${kafka-consumer-config.user-consumer-group-id}", topics = "${project-service.user-topic-name}")
  public void recieve(
      @Payload List<String> messages,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
    messages.forEach(this::handleMessage);
  }

  private void handleMessage(String data) {
    UserJsonModel message = helper.getEventPayload(data, UserJsonModel.class);
    if (message.getType().equals("ROLE_ACTIVATED")) {
      try {
        if (message.getActivatedRole().equals("ADMIN_PROJECT")) {
          listener.createManager(mapper.userJsonModelToManagerRegisteredMessage(message));
          log.info("{} was created: {}", message.getActivatedRole(), message.toString());
          return;
        }

        if (message.getActivatedRole().equals("LANDOWNER") || message.getActivatedRole().equals("INVESTOR")) {
          listener.createReceiver(mapper.userJsonToReceiverRegisteredMessage(message));
          log.info("{} was created: {}", message.getActivatedRole(), message.toString());
          return;
        }

      } catch (DataAlreadyExistsException e) {
        // Do nothing since it is a duplicate message
        log.warn("({}) {} is already exists. Ignoring message", message.getUserId(), message.getActivatedRole());
        return;
      }

      log.warn("Ignoring user message role: ({}) {}", message.getActivatedRole(), message.getUserId());
      return;
    }

    if (message.getType().equals("PROFILE_UPDATED")) {
      try {
        listener.updateReceiverBank(mapper.userJsonModelToReceiverProfileUpdatedMessage(message));
        log.info("{} bank account was updated: {}", message.getUserId(), message.toString());
      } catch (SameBankAccountException e) {
        log.warn("Ignoring message since bank account is the same as the old one");
      }
      return;
    }

    log.warn("Ignoring user message for: ({}) {}", message.getActivatedRole(), message.getUserId());

  }

}
