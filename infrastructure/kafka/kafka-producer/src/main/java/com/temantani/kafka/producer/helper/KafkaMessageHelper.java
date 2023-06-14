package com.temantani.kafka.producer.helper;

import java.util.function.BiConsumer;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temantani.domain.outbox.OutboxStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaMessageHelper {

  private final ObjectMapper mapper;

  public KafkaMessageHelper(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public <T extends SpecificRecordBase, U> ListenableFutureCallback<SendResult<String, T>> getKafkaCallback(
      String responseTopicName,
      T avroModel,
      U outboxMessage,
      BiConsumer<U, OutboxStatus> callback) {

    return new ListenableFutureCallback<SendResult<String, T>>() {
      @Override
      public void onSuccess(SendResult<String, T> result) {
        callback.accept(outboxMessage, OutboxStatus.COMPLETED);
      }

      @Override
      public void onFailure(Throwable ex) {
        callback.accept(outboxMessage, OutboxStatus.FAILED);
      }
    };
  }

  public <T> T getEventPayload(String payload, Class<T> type) {
    try {
      return mapper.readValue(payload, type);
    } catch (Exception e) {
      log.error("Unable to parse json into {}", type.getName(), e);
      throw new RuntimeException(e);
    }
  }
}
