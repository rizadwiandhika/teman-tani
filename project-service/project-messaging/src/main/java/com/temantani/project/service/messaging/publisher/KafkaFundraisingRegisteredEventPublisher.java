package com.temantani.project.service.messaging.publisher;

import java.util.function.BiConsumer;

import org.springframework.stereotype.Component;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel;
import com.temantani.kafka.producer.helper.KafkaMessageHelper;
import com.temantani.kafka.producer.service.KafkaProducer;
import com.temantani.project.service.domain.config.ProjectServiceConfigData;
import com.temantani.project.service.domain.outbox.model.fundraisingregistered.FundraisingRegisteredEventPayload;
import com.temantani.project.service.domain.outbox.model.fundraisingregistered.FundraisingRegisteredOutboxMessage;
import com.temantani.project.service.domain.ports.output.publisher.FundraisingRegisteredEventPublisher;
import com.temantani.project.service.messaging.mapper.ProjectMessagingDataMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaFundraisingRegisteredEventPublisher implements FundraisingRegisteredEventPublisher {

  private final KafkaProducer<String, FundraisingRegisteredAvroModel> producer;
  private final ProjectMessagingDataMapper mapper;
  private final ProjectServiceConfigData configData;
  private final KafkaMessageHelper helper;

  public KafkaFundraisingRegisteredEventPublisher(KafkaProducer<String, FundraisingRegisteredAvroModel> producer,
      ProjectMessagingDataMapper mapper, ProjectServiceConfigData configData, KafkaMessageHelper helper) {
    this.producer = producer;
    this.mapper = mapper;
    this.configData = configData;
    this.helper = helper;
  }

  @Override
  public void publish(FundraisingRegisteredOutboxMessage outbox,
      BiConsumer<FundraisingRegisteredOutboxMessage, OutboxStatus> callback) {
    FundraisingRegisteredEventPayload payload = helper.getEventPayload(outbox.getPayload(),
        FundraisingRegisteredEventPayload.class);

    String topic = configData.getFundraisingRegisteredTopicName();
    String key = outbox.getId().toString();
    FundraisingRegisteredAvroModel data = mapper
        .fundraisingRegisteredEventPayloadToFundraisingRegisteredAvroModel(payload);

    try {
      producer.send(topic, key, data, helper.getKafkaCallback(topic, data, outbox, callback));
    } catch (Exception e) {
      log.error("Unable to send to kafka for outbox: {}", outbox.getId(), e);
    }
  }

}
