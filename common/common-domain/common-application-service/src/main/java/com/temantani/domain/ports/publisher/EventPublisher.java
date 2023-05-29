package com.temantani.domain.ports.publisher;

import java.util.function.BiConsumer;

public interface EventPublisher<T, S> {
  void publish(T message, BiConsumer<T, S> callback);
}