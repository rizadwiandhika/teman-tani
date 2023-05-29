package com.temantani.user.service.domain.ports.output.repository.message;

public interface JsonMessagePublisher {
  void send(String key, String message);
}
