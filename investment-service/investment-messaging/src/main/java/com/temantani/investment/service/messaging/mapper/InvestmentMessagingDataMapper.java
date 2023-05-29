package com.temantani.investment.service.messaging.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.temantani.investment.service.domain.dto.message.CreateInvestorMessage;
import com.temantani.kafka.user.avro.model.UserAvroModel;

@Component
public class InvestmentMessagingDataMapper {

  public CreateInvestorMessage userAvroModelToCreateInvestorMessage(UserAvroModel model) {
    return CreateInvestorMessage.builder()
        .userId(UUID.fromString(model.getUserId()))
        .name(model.getName())
        .email(model.getEmail())
        .profilePictureUrl(model.getProfilePictureUrl())
        .build();
  }

}
