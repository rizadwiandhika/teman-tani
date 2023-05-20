package com.temantani.kafka.producer.helper;

import java.util.function.BiConsumer;

import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KafkaMessageHelper {

  private final ObjectMapper mapper;

  public KafkaMessageHelper(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public ListenableFutureCallback<SendResult<Object, Object>> getKafkaCallback(
      String responseTopicName,
      Object avroModel,
      Object outboxMessage,
      BiConsumer<Object, OutboxStatus> callback,
      String orderId,
      String avroModelName) {

    return new ListenableFutureCallback<SendResult<Object, Object>>() {
      @Override
      public void onSuccess(SendResult<Object, Object> result) {
        callback.accept(outboxMessage, OutboxStatus.COMPLETED);
      }

      @Override
      public void onFailure(Throwable ex) {
        callback.accept(outboxMessage, OutboxStatus.FAILED);
      }
    };
  }

}
