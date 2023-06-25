package com.temantani.user.service.messaging.mapper;

import org.springframework.stereotype.Component;

import com.temantani.kafka.user.avro.model.UserAvroModel;
import com.temantani.kafka.user.json.model.UserJsonModel;
import com.temantani.user.service.domain.outbox.model.UserEventPayload;

@Component
public class UserMessagingDataMapper {

  public UserAvroModel userEventPayloadToUserAvroModel(UserEventPayload payload) {
    return UserAvroModel.newBuilder()
        .setType(payload.getType())
        .setUserId(payload.getUserId().toString())
        .setName(payload.getName())
        .setEmail(payload.getEmail())
        .setPhoneNumber(payload.getPhoneNumber())
        .setProfilePictureUrl(payload.getProfilePicture())
        .setActivatedRole(payload.getActivatedRole())
        .setRoles(payload.getRoles())
        .setBank(payload.getBank())
        .setBankAccountNumber(payload.getBankAccountNumber())
        .setBankAccountHolderName(payload.getBankAccountHolderName())
        .setStreet(payload.getStreet())
        .setCity(payload.getCity())
        .setPostalCode(payload.getPostalCode())
        .build();
  }

  public UserJsonModel userEventPayloadToUserJsonModel(UserEventPayload payload) {
    return UserJsonModel.builder()
        .type(payload.getType())
        .userId(payload.getUserId().toString())
        .name(payload.getName())
        .email(payload.getEmail())
        .phoneNumber(payload.getPhoneNumber())
        .profilePictureUrl(payload.getProfilePicture())
        .activatedRole(payload.getActivatedRole())
        .roles(payload.getRoles())
        .bank(payload.getBank())
        .bankAccountNumber(payload.getBankAccountNumber())
        .bankAccountHolderName(payload.getBankAccountHolderName())
        .street(payload.getStreet())
        .city(payload.getCity())
        .postalCode(payload.getPostalCode())
        .build();
  }

}
