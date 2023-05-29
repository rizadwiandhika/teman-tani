package com.temantani.investment.service.messaging.publisher;

import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.temantani.investment.service.messaging.mapper.InvestmentMessagingDataMapper;
import com.temantani.kafka.land.avro.model.InvestmentPaidAvroModel;
import com.temantani.kafka.producer.service.KafkaProducer;

@Component
public class KafkaInvestmentPaidEventPublisher {

  // private final KafkaProducer<String, InvestmentPaidAvroModel> producer;
  // private final InvestmentMessagingDataMapper mapper;

}
