package com.temantani.project.service.messaging.publisher;

import java.util.function.BiConsumer;

import org.springframework.stereotype.Component;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.kafka.investment.avro.model.CloseFundraisingRequestAvroModel;
import com.temantani.kafka.producer.helper.KafkaMessageHelper;
import com.temantani.kafka.producer.service.KafkaProducer;
import com.temantani.project.service.domain.config.ProjectServiceConfigData;
import com.temantani.project.service.domain.outbox.model.closefundraisingrequested.CloseFundraisingRequestedOutboxMessage;
import com.temantani.project.service.domain.outbox.model.closefundraisingrequested.CloseFundraisingRqeustedEventPayload;
import com.temantani.project.service.domain.ports.output.publisher.CloseFundraisingRequestedEventPublisher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaCloseFundraisingRequestedEventPublisher implements CloseFundraisingRequestedEventPublisher {

  private final KafkaProducer<String, CloseFundraisingRequestAvroModel> producer;
  private final ProjectServiceConfigData configData;
  private final KafkaMessageHelper helper;

  public KafkaCloseFundraisingRequestedEventPublisher(KafkaProducer<String, CloseFundraisingRequestAvroModel> producer,
      ProjectServiceConfigData configData, KafkaMessageHelper helper) {
    this.producer = producer;
    this.configData = configData;
    this.helper = helper;
  }

  @Override
  public void publish(CloseFundraisingRequestedOutboxMessage outbox,
      BiConsumer<CloseFundraisingRequestedOutboxMessage, OutboxStatus> callback) {
    CloseFundraisingRqeustedEventPayload payload = helper.getEventPayload(outbox.getPayload(),
        CloseFundraisingRqeustedEventPayload.class);

    String topic = configData.getCloseFundraisingRequestTopicName();
    String key = outbox.getId().toString();
    CloseFundraisingRequestAvroModel data = new CloseFundraisingRequestAvroModel(payload.getProjectId().toString());

    try {
      producer.send(topic, key, data, helper.getKafkaCallback(topic, data, outbox, callback));
    } catch (Exception e) {
      log.error("Unable to send to kafka for outbox: {}", outbox.getId(), e);
    }
  }

}
