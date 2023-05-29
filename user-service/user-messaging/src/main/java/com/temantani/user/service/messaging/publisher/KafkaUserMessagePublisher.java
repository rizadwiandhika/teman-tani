package com.temantani.user.service.messaging.publisher;

import java.util.function.BiConsumer;

import org.springframework.stereotype.Component;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.kafka.producer.helper.KafkaMessageHelper;
import com.temantani.kafka.producer.service.KafkaProducer;
import com.temantani.kafka.user.avro.model.UserAvroModel;
import com.temantani.user.service.domain.config.UserServiceConfigData;
import com.temantani.user.service.domain.outbox.model.UserEventPayload;
import com.temantani.user.service.domain.outbox.model.UserOutboxMessage;
import com.temantani.user.service.domain.ports.output.repository.message.UserMesasgePublisher;
import com.temantani.user.service.messaging.mapper.UserMessagingDataMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaUserMessagePublisher implements UserMesasgePublisher {

  private final UserMessagingDataMapper mapper;
  private final UserServiceConfigData userServiceConfigData;
  private final KafkaMessageHelper kafkaMessageHelper;
  private final KafkaProducer<String, UserAvroModel> producer;

  public KafkaUserMessagePublisher(UserMessagingDataMapper mapper, UserServiceConfigData userServiceConfigData,
      KafkaMessageHelper kafkaMessageHelper, KafkaProducer<String, UserAvroModel> producer) {
    this.mapper = mapper;
    this.userServiceConfigData = userServiceConfigData;
    this.kafkaMessageHelper = kafkaMessageHelper;
    this.producer = producer;
  }

  @Override
  public void publish(UserOutboxMessage outboxMessage, BiConsumer<UserOutboxMessage, OutboxStatus> callback) {
    UserEventPayload payload = kafkaMessageHelper.getEventPayload(outboxMessage.getPayload(), UserEventPayload.class);

    String topic = userServiceConfigData.getUserTopicName();
    String key = payload.getUserId().toString();
    UserAvroModel value = mapper.userEventPayloadToUserAvroModel(payload);

    try {
      producer.send(topic, key, value, kafkaMessageHelper.getKafkaCallback(topic, value, outboxMessage, callback));
      log.info("Sent CustomerAvroModel to kafka topic. User id: {}", payload.getUserId().toString());
    } catch (Exception e) {
      log.error("Unable to send CustomerAvroModel to kafka topic", e);
    }

  }

}
