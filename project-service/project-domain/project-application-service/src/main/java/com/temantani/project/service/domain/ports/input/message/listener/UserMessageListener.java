package com.temantani.project.service.domain.ports.input.message.listener;

import com.temantani.project.service.domain.dto.message.user.ManagerRegisteredMessage;
import com.temantani.project.service.domain.dto.message.user.ReceiverProfileUpdatedMessage;
import com.temantani.project.service.domain.dto.message.user.ReceiverRegisteredMessage;

public interface UserMessageListener {

  void createReceiver(ReceiverRegisteredMessage message);

  void createManager(ManagerRegisteredMessage message);

  void updateReceiverBank(ReceiverProfileUpdatedMessage message);
}
