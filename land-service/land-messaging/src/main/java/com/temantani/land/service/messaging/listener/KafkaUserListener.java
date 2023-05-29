package com.temantani.land.service.messaging.listener;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.AdminRole;
import com.temantani.domain.valueobject.UserRole;
import com.temantani.kafka.KafkaConsumer;
import com.temantani.kafka.user.avro.model.UserAvroModel;
import com.temantani.land.service.domain.exception.DataAlreadyExists;
import com.temantani.land.service.domain.ports.input.message.UserMessageListener;
import com.temantani.land.service.messaging.mapper.LandMessagingDataMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaUserListener implements KafkaConsumer<UserAvroModel> {

  private final LandMessagingDataMapper mapper;
  private final UserMessageListener listener;

  public KafkaUserListener(LandMessagingDataMapper mapper, UserMessageListener listener) {
    this.mapper = mapper;
    this.listener = listener;
  }

  @Override
  @KafkaListener(id = "${kafka-consumer-config.user-consumer-group-id}", topics = "${land-service.user-topic-name}")
  public void recieve(
      @Payload List<UserAvroModel> messages,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

    messages.forEach((message) -> {
      String role = message.getActivatedRole();

      try {
        if (role.equals(AdminRole.ADMIN_LANDOWNER.name())) {
          listener.createApprover(mapper.userAvroModelToCreateApproverMessage(message));
          log.info("User message handled: {}", message);
          return;
        }

        if (role.equals(UserRole.LANDOWNER.name())) {
          listener.createBorrower(mapper.userAvroModelToCreateBorrowerMessage(message));
          log.info("User message handled: {}", message);
          return;
        }

        log.warn(
            "Ignoring KafkaUserListener user role: {} since it is not either ADMIN_LANDOWNER or LANDOWNER. Payload {}",
            message.getActivatedRole(), message);

      } catch (DataAlreadyExists e) {
        log.warn("Data already exists for id: {}", message.getUserId());
        log.warn("Skipping message");
      }

    });

  }

}