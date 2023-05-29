package com.temantani.land.service.messaging.mapper;

import org.springframework.stereotype.Component;

import com.temantani.kafka.land.avro.model.LandRegisteredAvroModel;
import com.temantani.kafka.user.avro.model.UserAvroModel;
import com.temantani.land.service.domain.dto.message.CreateApproverMessage;
import com.temantani.land.service.domain.dto.message.CreateBorrowerMessage;
import com.temantani.land.service.domain.outbox.model.LandRegisteredEventPayload;

@Component
public class LandMessagingDataMapper {

  public CreateApproverMessage userAvroModelToCreateApproverMessage(UserAvroModel message) {
    return CreateApproverMessage.builder()
        .approverId(message.getUserId())
        .email(message.getEmail())
        .name(message.getName())
        .phoneNumber(message.getPhoneNumber())
        .build();
  }

  public CreateBorrowerMessage userAvroModelToCreateBorrowerMessage(UserAvroModel message) {
    return CreateBorrowerMessage.builder()
        .borrowerId(message.getUserId())
        .email(message.getEmail())
        .name(message.getName())
        .phoneNumber(message.getPhoneNumber())
        .build();
  }

  public LandRegisteredAvroModel landRegisteredEventPayloadToLandRegisteredAvroModel(
      LandRegisteredEventPayload payload) {
    return LandRegisteredAvroModel.newBuilder()
        .setLandId(payload.getLandId().toString())
        .setOwnerId(payload.getOwnerId().toString())
        .setApproverId(payload.getApproverId().toString())
        .setApprovedAt(payload.getApprovedAt().toInstant())
        .setProposedAt(payload.getProposedAt().toInstant())
        .setHarvestSuitabilities(payload.getHarvestSuitabilities())
        .setGroundHeightValue(payload.getGroundHeightValue())
        .setGroundHeightUnit(payload.getGroundHeightUnit())
        .setSoilPh(payload.getSoilPh())
        .setWaterAvailabilityStatus(payload.getWaterAvailabilityStatus())
        .setLandUsageHistory(payload.getLandUsageHistory())
        .setLandStatus(payload.getLandStatus())
        .setAreaValue(payload.getAreaValue())
        .setAreaUnit(payload.getAreaUnit())
        .setStreet(payload.getStreet())
        .setCity(payload.getCity())
        .setPostalCode(payload.getPostalCode())
        .setCertificateUrl(payload.getCertificateUrl())
        .setPhotos(payload.getPhotos())
        .build();
  }

}
