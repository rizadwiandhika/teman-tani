package com.temantani.project.service.messaging.listener;

import java.util.List;
import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.Money;
import com.temantani.kafka.KafkaConsumer;
import com.temantani.kafka.producer.helper.KafkaMessageHelper;
import com.temantani.kafka.project.json.model.OrderCreatedJsonModel;
import com.temantani.project.service.domain.exception.IncomeHasBeenAddedException;
import com.temantani.project.service.domain.ports.input.message.listener.OrderCreatedMessageListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaOrderCreatedListener implements KafkaConsumer<String> {

  private final OrderCreatedMessageListener listener;
  private final KafkaMessageHelper helper;

  public KafkaOrderCreatedListener(OrderCreatedMessageListener listener, KafkaMessageHelper helper) {
    this.listener = listener;
    this.helper = helper;
  }

  @Override
  @KafkaListener(id = "${kafka-consumer-config.order-created-consumer-group-id}", topics = "${project-service.order-created-topic-name}")
  public void recieve(@Payload List<String> messages,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
    messages.forEach(this::handle);

  }

  private void handle(String data) {
    OrderCreatedJsonModel message = helper.getEventPayload(data, OrderCreatedJsonModel.class);
    try {
      log.info("Handling order: {}", message);
      if (message.getProjectId() == null || message.getProjectId().isEmpty()) {
        throw new RuntimeException("Project ID is empty");
      }
      if (message.getOrderId() == null || message.getOrderId().isEmpty()) {
        throw new RuntimeException("Order ID is empty");
      }

      listener.addIncomeFromOrder(UUID.fromString(message.getProjectId()), UUID.fromString(message.getOrderId()),
          new Money(message.getAmount()));
      log.info("Order created was handled for order: {}", message.getOrderId());
    } catch (IncomeHasBeenAddedException e) {
      log.warn("Ignoring message since Income for order id: {} has been added previously", message.getOrderId());
    }
  }

}
