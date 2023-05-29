package com.temantani.project.service.messaging.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.BankAccount;
import com.temantani.kafka.land.avro.model.InvestmentPaidAvroModel;
import com.temantani.kafka.land.avro.model.LandRegisteredAvroModel;
import com.temantani.kafka.user.avro.model.UserAvroModel;
import com.temantani.project.service.domain.dto.message.investment.InvestmentPaidMessage;
import com.temantani.project.service.domain.dto.message.land.LandRegisteredMessage;
import com.temantani.project.service.domain.dto.message.user.ManagerRegisteredMessage;
import com.temantani.project.service.domain.dto.message.user.ReceiverProfileUpdatedMessage;
import com.temantani.project.service.domain.dto.message.user.ReceiverRegisteredMessage;

@Component
public class ProjectMessagingDataMapper {

  public ManagerRegisteredMessage userAvroModelToManagerRegisteredMessage(UserAvroModel message) {
    return ManagerRegisteredMessage.builder()
        .managerId(UUID.fromString(message.getUserId()))
        .email(message.getEmail())
        .email(message.getName())
        .build();
  }

  public ReceiverRegisteredMessage userAvroModelToReceiverRegisteredMessage(UserAvroModel message) {
    return ReceiverRegisteredMessage.builder()
        .receiverId(UUID.fromString(message.getUserId()))
        .bankAccount(
            new BankAccount(message.getBank(), message.getBankAccountNumber(), message.getBankAccountHolderName()))
        .build();
  }

  public ReceiverProfileUpdatedMessage userAvroModelToReceiverProfileUpdatedMessage(UserAvroModel message) {
    return ReceiverProfileUpdatedMessage.builder()
        .receiverId(UUID.fromString(message.getUserId()))
        .bankAccount(
            new BankAccount(message.getBank(), message.getBankAccountNumber(), message.getBankAccountHolderName()))
        .build();
  }

  public LandRegisteredMessage LandRegisteredAvroModelToLandRegisteredMessage(LandRegisteredAvroModel message) {
    return LandRegisteredMessage.builder()
        .build();
  }

  public InvestmentPaidMessage investmentPaidAvroModelToInvestmentPaidMessage(InvestmentPaidAvroModel message) {
    return InvestmentPaidMessage.builder()
        .investemntId(UUID.fromString(message.getInvestmentId()))
        .projectId(UUID.fromString(message.getProjectId()))
        .investorId(UUID.fromString(message.getInvestorId()))
        .amount(message.getAmount())
        .build();
  }

}
