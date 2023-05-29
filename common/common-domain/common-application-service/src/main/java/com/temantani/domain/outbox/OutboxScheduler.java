package com.temantani.domain.outbox;

public interface OutboxScheduler {

  void processOutbox();

}
