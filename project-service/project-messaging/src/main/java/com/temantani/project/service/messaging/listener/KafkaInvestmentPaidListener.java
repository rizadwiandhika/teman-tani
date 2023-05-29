package com.temantani.project.service.messaging.listener;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.temantani.kafka.KafkaConsumer;
import com.temantani.kafka.land.avro.model.InvestmentPaidAvroModel;
import com.temantani.project.service.domain.exception.DataAlreadyExistsException;
import com.temantani.project.service.domain.ports.input.message.listener.InvestmentPaidMessageListener;
import com.temantani.project.service.messaging.mapper.ProjectMessagingDataMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaInvestmentPaidListener implements KafkaConsumer<InvestmentPaidAvroModel> {

  private final InvestmentPaidMessageListener listener;
  private final ProjectMessagingDataMapper mapper;

  public KafkaInvestmentPaidListener(InvestmentPaidMessageListener listener, ProjectMessagingDataMapper mapper) {
    this.listener = listener;
    this.mapper = mapper;

  }

  @Override
  @KafkaListener(id = "${kafka-consumer-config.investment-paid-consumer-group-id}", topics = "${project-service.investment-paid-topic-name}")
  public void recieve(
      @Payload List<InvestmentPaidAvroModel> messages,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
    messages.forEach(this::handle);
  }

  private void handle(InvestmentPaidAvroModel message) {
    try {
      listener.addInvestmentToProject(mapper.investmentPaidAvroModelToInvestmentPaidMessage(message));
    } catch (DataAlreadyExistsException e) {
      log.warn("Investment already exists: {}", message.getInvestmentId());
    }
  }

}
