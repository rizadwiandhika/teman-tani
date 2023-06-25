package com.temantani.project.service.messaging.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.Address;
import com.temantani.domain.valueobject.BankAccount;
import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.kafka.investment.avro.model.CloseFundraisingInvestmentResponseAvroModel;
import com.temantani.kafka.investment.avro.model.FundraisingRegisteredAvroModel;
import com.temantani.kafka.investment.json.model.FundraisingRegisteredJsonModel;
import com.temantani.kafka.investment.json.model.InvestmentJsonModel;
import com.temantani.kafka.land.avro.model.LandRegisteredAvroModel;
import com.temantani.kafka.land.json.model.LandRegisteredJsonModel;
import com.temantani.kafka.user.avro.model.UserAvroModel;
import com.temantani.kafka.user.json.model.UserJsonModel;
import com.temantani.project.service.domain.dto.message.land.LandRegisteredMessage;
import com.temantani.project.service.domain.dto.message.user.ManagerRegisteredMessage;
import com.temantani.project.service.domain.dto.message.user.ReceiverProfileUpdatedMessage;
import com.temantani.project.service.domain.dto.message.user.ReceiverRegisteredMessage;
import com.temantani.project.service.domain.entity.Investment;
import com.temantani.project.service.domain.outbox.model.fundraisingregistered.FundraisingRegisteredEventPayload;
import com.temantani.project.service.domain.valueobject.LandAvailabilityStatus;

@Component
public class ProjectMessagingDataMapper {

  public ManagerRegisteredMessage userAvroModelToManagerRegisteredMessage(UserAvroModel message) {
    return ManagerRegisteredMessage.builder()
        .managerId(UUID.fromString(message.getUserId()))
        .email(message.getEmail())
        .name(message.getName())
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
        .landId(UUID.fromString(message.getLandId()))
        .ownerId(UUID.fromString(message.getOwnerId()))
        .address(new Address(message.getStreet(), message.getCity(), message.getPostalCode()))
        .landStatus(LandAvailabilityStatus.AVAILABLE)
        .build();
  }

  public FundraisingRegisteredAvroModel fundraisingRegisteredEventPayloadToFundraisingRegisteredAvroModel(
      FundraisingRegisteredEventPayload payload) {
    return FundraisingRegisteredAvroModel.newBuilder()
        .setId(payload.getProjectId())
        .setLandId(payload.getLandId())
        .setDescription(payload.getDescription())
        .setHarvest(payload.getHarvest())
        .setFundraisingTarget(payload.getFundraisingTarget())
        .setFundraisingDeadline(payload.getFundraisingDeadline().toInstant())
        .setEstimatedFinished(payload.getEstimatedFinished().toInstant())
        .setCreatedAt(payload.getCreatedAt().toInstant())
        .build();
  }

  public Investment closeFundraisingInvestmentResponseAvroModelToInvestment(
      CloseFundraisingInvestmentResponseAvroModel i,
      ProjectId projectId) {
    return Investment.builder()
        .id(new InvestmentId(UUID.fromString(i.getInvestmentId())))
        .projectId(projectId)
        .investorId(new UserId(UUID.fromString(i.getInvestorId())))
        .amount(new Money(i.getAmount()))
        .build();
  }

  public ReceiverRegisteredMessage userJsonToReceiverRegisteredMessage(UserJsonModel message) {
    return ReceiverRegisteredMessage.builder()
        .receiverId(UUID.fromString(message.getUserId()))
        .bankAccount(
            new BankAccount(message.getBank(), message.getBankAccountNumber(), message.getBankAccountHolderName()))
        .build();
  }

  public ManagerRegisteredMessage userJsonModelToManagerRegisteredMessage(UserJsonModel message) {
    return ManagerRegisteredMessage.builder()
        .managerId(UUID.fromString(message.getUserId()))
        .email(message.getEmail())
        .name(message.getName())
        .build();
  }

  public ReceiverProfileUpdatedMessage userJsonModelToReceiverProfileUpdatedMessage(UserJsonModel message) {
    return ReceiverProfileUpdatedMessage.builder()
        .receiverId(UUID.fromString(message.getUserId()))
        .bankAccount(
            new BankAccount(message.getBank(), message.getBankAccountNumber(), message.getBankAccountHolderName()))
        .build();
  }

  public LandRegisteredMessage LandRegisteredJsonModelToLandRegisteredMessage(LandRegisteredJsonModel message) {
    return LandRegisteredMessage.builder()
        .landId(UUID.fromString(message.getLandId()))
        .ownerId(UUID.fromString(message.getOwnerId()))
        .address(new Address(message.getStreet(), message.getCity(), message.getPostalCode()))
        .landStatus(LandAvailabilityStatus.AVAILABLE)
        .build();
  }

  public Investment closeFundraisingInvestmentResponseJsonModelToInvestment(InvestmentJsonModel i,
      ProjectId projectId) {
    return Investment.builder()
        .id(new InvestmentId(UUID.fromString(i.getInvestmentId())))
        .projectId(projectId)
        .investorId(new UserId(UUID.fromString(i.getInvestorId())))
        .amount(new Money(i.getAmount()))
        .build();
  }

  public FundraisingRegisteredJsonModel fundraisingRegisteredEventPayloadToFundraisingRegisteredJsonModel(
      FundraisingRegisteredEventPayload payload) {
    return FundraisingRegisteredJsonModel.builder()
        .id(payload.getProjectId())
        .landId(payload.getLandId())
        .description(payload.getDescription())
        .harvest(payload.getHarvest())
        .fundraisingTarget(payload.getFundraisingTarget())
        .fundraisingDeadline(payload.getFundraisingDeadline())
        .estimatedFinished(payload.getEstimatedFinished())
        .createdAt(payload.getCreatedAt())
        .build();

  }

}
