package com.temantani.user.service.messaging.publisher;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.temantani.user.service.domain.ports.output.repository.message.JsonMessagePublisher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaJsonMessagePublisher implements JsonMessagePublisher {
  private final KafkaTemplate<String, String> kafkaTemplate;

  public KafkaJsonMessagePublisher(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void send(String key, String message) {
    String topic = "json-topic";

    try {
      ListenableFuture<SendResult<String, String>> kafkaFutureResult = kafkaTemplate.send(topic, key, message);
      kafkaFutureResult.addCallback(getCallback());
    } catch (Exception e) {
      log.error("failed to publish message", e);
    }
  }

  private ListenableFutureCallback<SendResult<String, String>> getCallback() {
    return new ListenableFutureCallback<SendResult<String, String>>() {
      @Override
      public void onFailure(Throwable ex) {
        log.error("failed to publish message", ex);
      }

      public void onSuccess(SendResult<String, String> r) {
        log.info("message published");
      };
    };
  }
}
