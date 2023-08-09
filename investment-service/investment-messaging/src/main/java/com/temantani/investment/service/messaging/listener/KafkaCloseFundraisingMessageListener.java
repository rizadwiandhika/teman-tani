package com.temantani.investment.service.messaging.listener;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.ProjectId;
import com.temantani.investment.service.domain.exception.FundraisingClosedException;
import com.temantani.investment.service.domain.ports.input.message.CloseFundraisingMessageListener;
import com.temantani.kafka.KafkaConsumer;
import com.temantani.kafka.investment.json.model.CloseFundraisingRequestJsonModel;
import com.temantani.kafka.producer.helper.KafkaMessageHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaCloseFundraisingMessageListener implements KafkaConsumer<String> {

  private final CloseFundraisingMessageListener listener;
  private final KafkaMessageHelper helper;

  public KafkaCloseFundraisingMessageListener(CloseFundraisingMessageListener listener, KafkaMessageHelper helper) {
    this.listener = listener;
    this.helper = helper;
  }

  @Override
  @KafkaListener(id = "${kafka-consumer-config.close-fundraising-request-consumer-group-id}", topics = "${investment-service.close-fundraising-request-topic-name}")
  public void recieve(
      @Payload List<String> messages,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
    messages.forEach(this::handle);
  }

  private void handle(String data) {
    CloseFundraisingRequestJsonModel model = helper.getEventPayload(data, CloseFundraisingRequestJsonModel.class);
    try {
      listener.closeFundraising(new ProjectId(UUID.fromString(model.getProjectId())));
      log.info("Fundraising close request has been processed for: {}", model.getProjectId());
    } catch (FundraisingClosedException e) {
      log.warn("Funraising: {} is already closed", model.getProjectId(), e);
      log.warn("Skipping message");
    } catch (OptimisticLockingFailureException e) {
      log.warn("OptimisticLockingFailureException occures. Ignoring", e);
      log.warn("Skipping message");
    }
  }

}
