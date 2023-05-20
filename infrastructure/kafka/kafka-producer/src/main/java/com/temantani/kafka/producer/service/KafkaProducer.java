package com.temantani.kafka.producer.service;

import java.io.Serializable;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

public interface KafkaProducer<K extends Serializable, V extends SpecificRecordBase> {

  void send(String topic, K key, V value, ListenableFutureCallback<SendResult<K, V>> callback);

}
