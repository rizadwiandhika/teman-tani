package com.temantani.land.service.messaging.publisher;

import java.util.function.BiConsumer;

import org.springframework.stereotype.Component;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.kafka.land.avro.model.LandRegisteredAvroModel;
import com.temantani.kafka.producer.helper.KafkaMessageHelper;
import com.temantani.kafka.producer.service.KafkaProducer;
import com.temantani.land.service.domain.config.LandServiceConfigData;
import com.temantani.land.service.domain.outbox.model.LandRegisteredEventPayload;
import com.temantani.land.service.domain.outbox.model.LandRegistetedOutboxMessage;
import com.temantani.land.service.domain.ports.output.message.LandRegisteredMessagePublisher;
import com.temantani.land.service.messaging.mapper.LandMessagingDataMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaLandRegisteredPublisher implements LandRegisteredMessagePublisher {

  private final KafkaProducer<String, LandRegisteredAvroModel> producer;
  private final KafkaMessageHelper kafkaHelper;
  private final LandMessagingDataMapper mapper;
  private final LandServiceConfigData landServiceConfigData;

  public KafkaLandRegisteredPublisher(KafkaProducer<String, LandRegisteredAvroModel> producer,
      KafkaMessageHelper kafkaHelper, LandMessagingDataMapper mapper, LandServiceConfigData landServiceConfigData) {
    this.producer = producer;
    this.kafkaHelper = kafkaHelper;
    this.mapper = mapper;
    this.landServiceConfigData = landServiceConfigData;
  }

  @Override
  public void publish(LandRegistetedOutboxMessage outbox,
      BiConsumer<LandRegistetedOutboxMessage, OutboxStatus> callback) {
    LandRegisteredEventPayload payload = kafkaHelper.getEventPayload(outbox.getPayload(),
        LandRegisteredEventPayload.class);

    String topic = landServiceConfigData.getLandRegisteredTopicName();
    String key = outbox.getId().toString();
    LandRegisteredAvroModel value = mapper.landRegisteredEventPayloadToLandRegisteredAvroModel(payload);

    try {
      producer.send(topic, key, value, kafkaHelper.getKafkaCallback(topic, value, outbox, callback));
      log.info("Sent LandRegisteredAvroModel to kafka topic. Land id: {}", payload.getLandId().toString());
    } catch (Exception e) {
      log.error("Unable to send LandRegisteredAvroModel to kafka topic", e);
    }
  }

}
