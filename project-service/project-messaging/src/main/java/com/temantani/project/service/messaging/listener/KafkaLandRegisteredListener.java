package com.temantani.project.service.messaging.listener;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.temantani.domain.exception.DataAlreadyExistsException;
import com.temantani.kafka.KafkaConsumer;
import com.temantani.kafka.land.json.model.LandRegisteredJsonModel;
import com.temantani.kafka.producer.helper.KafkaMessageHelper;
import com.temantani.project.service.domain.ports.input.message.listener.LandRegisteredMessageListener;
import com.temantani.project.service.messaging.mapper.ProjectMessagingDataMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaLandRegisteredListener implements KafkaConsumer<String> {

  private final ProjectMessagingDataMapper mapper;
  private final LandRegisteredMessageListener listener;
  private final KafkaMessageHelper helper;

  public KafkaLandRegisteredListener(ProjectMessagingDataMapper mapper, LandRegisteredMessageListener listener,
      KafkaMessageHelper helper) {
    this.mapper = mapper;
    this.listener = listener;
    this.helper = helper;
  }

  @Override
  @KafkaListener(id = "${kafka-consumer-config.land-registered-consumer-group-id}", topics = "${project-service.land-registered-topic-name}")
  public void recieve(
      @Payload List<String> messages,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
    messages.forEach(this::handle);
  }

  private void handle(String data) {
    LandRegisteredJsonModel message = helper.getEventPayload(data, LandRegisteredJsonModel.class);
    try {
      listener.createLand(mapper.LandRegisteredJsonModelToLandRegisteredMessage(message));
      log.info("Land was created: {}", message.toString());
    } catch (DataAlreadyExistsException e) {
      log.warn("Ignoring land message since land: {} is already exists.", message.getLandId());
    }
  }

}
