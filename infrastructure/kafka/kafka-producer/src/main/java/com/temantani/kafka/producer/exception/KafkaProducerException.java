package com.temantani.kafka.producer.exception;

public class KafkaProducerException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public KafkaProducerException(String message) {
    super(message);
  }

  public KafkaProducerException(String message, Throwable cause) {
    super(message, cause);
  }

}
