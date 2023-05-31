package com.temantani.investment.service.messaging.listener;

import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.temantani.domain.DomainConstant;
import com.temantani.domain.exception.DataAlreadyExistsException;
import com.temantani.domain.valueobject.Money;
import com.temantani.investment.service.domain.ports.input.message.FundraisingRegisteredMessageListener;
import com.temantani.kafka.KafkaConsumer;
import com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaFundraisingRegisteredListener implements KafkaConsumer<FundraisingRegisteredAvroModel> {

  private final FundraisingRegisteredMessageListener listener;

  public KafkaFundraisingRegisteredListener(FundraisingRegisteredMessageListener listener) {
    this.listener = listener;
  }

  @Override
  @KafkaListener(id = "${kafka-consumer-config.fundraising-registered-consumer-group-id}", topics = "${investment-service.fundraising-registered-topic-name}")
  public void recieve(
      @Payload List<FundraisingRegisteredAvroModel> messages,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
    messages.forEach(this::handle);
  }

  private void handle(FundraisingRegisteredAvroModel model) {
    try {
      listener.registerFundraisingProject(
          UUID.fromString(model.getId()),
          new Money(model.getFundraisingTarget()),
          model.getFundraisingDeadline().atZone(ZoneId.of(DomainConstant.TIMEZONE)));
    } catch (DataAlreadyExistsException e) {
      log.warn("Project is already exists: {}", model.getId());
      log.warn("Skipping message");
    }
  }

}
