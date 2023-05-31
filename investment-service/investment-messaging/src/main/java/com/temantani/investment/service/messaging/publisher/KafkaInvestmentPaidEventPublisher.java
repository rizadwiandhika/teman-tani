package com.temantani.investment.service.messaging.publisher;

import java.util.function.BiConsumer;

import org.springframework.stereotype.Component;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.investment.service.domain.outbox.model.investmentpaid.InvestmentPaidOutboxMessage;
import com.temantani.investment.service.domain.ports.output.publisher.InvestmentPaidEventPublisher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaInvestmentPaidEventPublisher implements InvestmentPaidEventPublisher {

  @Override
  public void publish(InvestmentPaidOutboxMessage message,
      BiConsumer<InvestmentPaidOutboxMessage, OutboxStatus> callback) {
    log.warn("Ignore publishing InvestmentPaidOutboxMessage");
  }

  // private final KafkaProducer<String, InvestmentPaidAvroModel> producer;
  // private final InvestmentMessagingDataMapper mapper;

}
