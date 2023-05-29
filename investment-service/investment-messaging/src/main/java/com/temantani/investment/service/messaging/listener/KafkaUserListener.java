package com.temantani.investment.service.messaging.listener;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.UserRole;
import com.temantani.investment.service.domain.exception.InvestorAlreadyExistsException;
import com.temantani.investment.service.domain.ports.input.message.UserMessageListener;
import com.temantani.investment.service.messaging.mapper.InvestmentMessagingDataMapper;
import com.temantani.kafka.KafkaConsumer;
import com.temantani.kafka.user.avro.model.UserAvroModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaUserListener implements KafkaConsumer<UserAvroModel> {

  private final UserMessageListener userMessageListener;
  private final InvestmentMessagingDataMapper mapper;

  public KafkaUserListener(UserMessageListener userMessageListener, InvestmentMessagingDataMapper mapper) {
    this.userMessageListener = userMessageListener;
    this.mapper = mapper;
  }

  @Override
  @KafkaListener(id = "${kafka-consumer-config.user-consumer-group-id}", topics = "${investment-service.user-topic-name}")
  public void recieve(
      @Payload List<UserAvroModel> messages,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
    messages.forEach((message) -> {
      if (message.getActivatedRole().equals(UserRole.INVESTOR.name()) == false) {
        log.warn("Ignoring KafkaUserListener user role: {} since it is not INVESTOR. Payload {}",
            message.getActivatedRole(), message);
        return;
      }

      try {
        userMessageListener.createInvestor(mapper.userAvroModelToCreateInvestorMessage(message));
        log.info("User message handled: {}", message);
      } catch (InvestorAlreadyExistsException e) {
        log.warn("Investor already exists for id: {}", message.getUserId());
        log.warn("Skipping message");
      }

    });

  }

}