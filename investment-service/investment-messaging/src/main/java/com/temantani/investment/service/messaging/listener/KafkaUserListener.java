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
import com.temantani.kafka.producer.helper.KafkaMessageHelper;
import com.temantani.kafka.user.json.model.UserJsonModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaUserListener implements KafkaConsumer<String> {

  private final UserMessageListener userMessageListener;
  private final InvestmentMessagingDataMapper mapper;
  private final KafkaMessageHelper helper;

  public KafkaUserListener(UserMessageListener userMessageListener, InvestmentMessagingDataMapper mapper,
      KafkaMessageHelper helper) {
    this.userMessageListener = userMessageListener;
    this.mapper = mapper;
    this.helper = helper;
  }

  @Override
  @KafkaListener(id = "${kafka-consumer-config.user-consumer-group-id}", topics = "${investment-service.user-topic-name}")
  public void recieve(
      @Payload List<String> messages,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
    messages.forEach((message) -> {
      UserJsonModel model = helper.getEventPayload(message, UserJsonModel.class);
      if (model.getActivatedRole().equals(UserRole.INVESTOR.name()) == false) {
        log.warn("Ignoring KafkaUserListener user role: {} since it is not INVESTOR. Payload {}",
            model.getActivatedRole(), model);
        return;
      }

      try {
        userMessageListener.createInvestor(mapper.userJsonModelToCreateInvestorMessage(model));
        log.info("User message handled: {}", model);
      } catch (InvestorAlreadyExistsException e) {
        log.warn("Investor already exists for id: {}", model.getUserId());
        log.warn("Skipping message");
      }

    });

  }

}