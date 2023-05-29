package com.temantani.project.service.domain.dto.message.user;

import java.util.UUID;

import com.temantani.domain.valueobject.BankAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ReceiverProfileUpdatedMessage {

  private UUID receiverId;
  private BankAccount bankAccount;

}
