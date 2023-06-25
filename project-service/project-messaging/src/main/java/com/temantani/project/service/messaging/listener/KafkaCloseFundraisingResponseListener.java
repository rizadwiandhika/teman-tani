package com.temantani.project.service.messaging.listener;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.ProjectId;
import com.temantani.kafka.KafkaConsumer;
import com.temantani.kafka.investment.json.model.CloseFundraisingResponseJsonModel;
import com.temantani.kafka.producer.helper.KafkaMessageHelper;
import com.temantani.project.service.domain.entity.Investment;
import com.temantani.project.service.domain.exception.ProceededHiringException;
import com.temantani.project.service.domain.ports.input.message.listener.CloseFundraisingResponseMessageListener;
import com.temantani.project.service.messaging.mapper.ProjectMessagingDataMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaCloseFundraisingResponseListener implements KafkaConsumer<String> {

  private final CloseFundraisingResponseMessageListener listener;
  private final ProjectMessagingDataMapper mapper;
  private final KafkaMessageHelper helper;

  public KafkaCloseFundraisingResponseListener(CloseFundraisingResponseMessageListener listener,
      ProjectMessagingDataMapper mapper, KafkaMessageHelper helper) {
    this.listener = listener;
    this.mapper = mapper;
    this.helper = helper;
  }

  @Override
  @KafkaListener(id = "${kafka-consumer-config.close-fundraising-response-consumer-group-id}", topics = "${project-service.close-fundraising-response-topic-name}")
  public void recieve(
      @Payload List<String> messages,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

    messages.forEach(this::handle);

  }

  private void handle(String data) {
    CloseFundraisingResponseJsonModel message = helper.getEventPayload(data, CloseFundraisingResponseJsonModel.class);
    ProjectId projectId = ProjectId.fromString(message.getProjectId());
    List<Investment> investments = message.getInvestments().stream()
        .map(i -> mapper.closeFundraisingInvestmentResponseJsonModelToInvestment(i, projectId))
        .collect(Collectors.toList());

    if (message.getStatus().equals("CLOSING")) {
      log.warn("Ignoring project: {} proceeded to hiring since the status is still CLOSING", projectId.getValue());
      return;
    }

    try {
      listener.proceededToHiring(projectId, investments);
      log.info("Project: {} was proceeded to HIRING", projectId.getValue());
    } catch (ProceededHiringException e) {
      log.warn(e.getMessage());
      log.warn("Ignoring proceeded to hiring since probably it has been executed previously");
    } catch (OptimisticLockingFailureException e) {
      log.warn("Optimistic locking happen for: {}", message.getProjectId());
      log.warn("Ignoring proceeded to hiring");
    }
  }

}
