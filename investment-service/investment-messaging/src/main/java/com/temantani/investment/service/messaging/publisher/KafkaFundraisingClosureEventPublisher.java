package com.temantani.investment.service.messaging.publisher;

import java.util.function.BiConsumer;

import org.springframework.stereotype.Component;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.investment.service.domain.config.InvestmentServiceConfigData;
import com.temantani.investment.service.domain.outbox.model.fundraisingclosure.FundraisingClosureEventPayload;
import com.temantani.investment.service.domain.outbox.model.fundraisingclosure.FundraisingClosureOutboxMessage;
import com.temantani.investment.service.domain.ports.output.publisher.FundraisingClosureEventPublisher;
import com.temantani.investment.service.messaging.mapper.InvestmentMessagingDataMapper;
import com.temantani.kafka.investment.avro.model.CloseFundraisingResponseAvroModel;
import com.temantani.kafka.producer.helper.KafkaMessageHelper;
import com.temantani.kafka.producer.service.KafkaProducer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaFundraisingClosureEventPublisher implements FundraisingClosureEventPublisher {

  private final KafkaProducer<String, CloseFundraisingResponseAvroModel> publisher;
  private final InvestmentMessagingDataMapper mapper;
  private final KafkaMessageHelper helper;
  private final InvestmentServiceConfigData configData;

  public KafkaFundraisingClosureEventPublisher(KafkaProducer<String, CloseFundraisingResponseAvroModel> publisher,
      InvestmentMessagingDataMapper mapper, KafkaMessageHelper helper, InvestmentServiceConfigData configData) {
    this.publisher = publisher;
    this.mapper = mapper;
    this.helper = helper;
    this.configData = configData;
  }

  @Override
  public void publish(FundraisingClosureOutboxMessage message,
      BiConsumer<FundraisingClosureOutboxMessage, OutboxStatus> callback) {
    FundraisingClosureEventPayload payload = helper.getEventPayload(message.getPayload(),
        FundraisingClosureEventPayload.class);

    String topic = configData.getCloseFundraisingResponseTopicName();
    String key = message.getId().toString();
    CloseFundraisingResponseAvroModel model = mapper
        .fundraisingClosureEventPayloadToCloseFundraisingResponseAvroModel(payload);

    try {
      publisher.send(topic, key, model, helper.getKafkaCallback(topic, model, message, callback));
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

}
