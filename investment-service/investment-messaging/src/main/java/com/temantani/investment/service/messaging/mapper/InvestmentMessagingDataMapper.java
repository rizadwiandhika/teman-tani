package com.temantani.investment.service.messaging.mapper;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.temantani.investment.service.domain.dto.message.CreateInvestorMessage;
import com.temantani.investment.service.domain.outbox.model.fundraisingclosure.FundraisingClosureEventPayload;
import com.temantani.kafka.investment.avro.model.CloseFundraisingInvestmentResponseAvroModel;
import com.temantani.kafka.investment.avro.model.CloseFundraisingResponseAvroModel;
import com.temantani.kafka.investment.avro.model.CloseFundraisingStatusResponseAvroModel;
import com.temantani.kafka.investment.json.model.CloseFundraisingResponseJsonModel;
import com.temantani.kafka.investment.json.model.InvestmentJsonModel;
import com.temantani.kafka.user.avro.model.UserAvroModel;
import com.temantani.kafka.user.json.model.UserJsonModel;

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

  public CloseFundraisingResponseAvroModel fundraisingClosureEventPayloadToCloseFundraisingResponseAvroModel(
      FundraisingClosureEventPayload payload) {
    return CloseFundraisingResponseAvroModel.newBuilder()
        .setStatus(CloseFundraisingStatusResponseAvroModel.valueOf(payload.getStatus()))
        .setProjectId(payload.getProjectId())
        .setInvestments(payload.getInvestments() == null ? new ArrayList<>()
            : payload.getInvestments()
                .stream()
                .map(i -> CloseFundraisingInvestmentResponseAvroModel.newBuilder()
                    .setInvestmentId(i.getInvestmentId())
                    .setInvestorId(i.getInvestorId())
                    .setAmount(i.getAmount())
                    .build())
                .collect(Collectors.toList()))
        .build();
  }

  public CreateInvestorMessage userJsonModelToCreateInvestorMessage(UserJsonModel model) {
    return CreateInvestorMessage.builder()
        .userId(UUID.fromString(model.getUserId()))
        .name(model.getName())
        .email(model.getEmail())
        .profilePictureUrl(model.getProfilePictureUrl())
        .build();
  }

  public CloseFundraisingResponseJsonModel fundraisingClosureEventPayloadToCloseFundraisingResponseJsonModel(
      FundraisingClosureEventPayload payload) {
    return CloseFundraisingResponseJsonModel.builder()
        .status(payload.getStatus())
        .projectId(payload.getProjectId())
        .investments(payload.getInvestments() == null ? new ArrayList<>()
            : payload.getInvestments()
                .stream()
                .map(i -> InvestmentJsonModel.builder()
                    .investmentId(i.getInvestmentId())
                    .investorId(i.getInvestorId())
                    .amount(i.getAmount())
                    .build())
                .collect(Collectors.toList()))
        .build();
  }

}
