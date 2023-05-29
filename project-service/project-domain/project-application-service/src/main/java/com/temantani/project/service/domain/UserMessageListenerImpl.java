package com.temantani.project.service.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.domain.dto.message.user.ManagerRegisteredMessage;
import com.temantani.project.service.domain.dto.message.user.ReceiverProfileUpdatedMessage;
import com.temantani.project.service.domain.dto.message.user.ReceiverRegisteredMessage;
import com.temantani.project.service.domain.entity.Manager;
import com.temantani.project.service.domain.entity.Receiver;
import com.temantani.project.service.domain.exception.DataNotFoundException;
import com.temantani.project.service.domain.exception.ProjectDomainException;
import com.temantani.project.service.domain.mapper.ProjectDataMapper;
import com.temantani.project.service.domain.ports.input.message.listener.UserMessageListener;
import com.temantani.project.service.domain.ports.output.repository.ManagerRepository;
import com.temantani.project.service.domain.ports.output.repository.ReceiverRepository;

@Component
public class UserMessageListenerImpl implements UserMessageListener {

  private final ProjectDataMapper mapper;
  private final ManagerRepository managerRepository;
  private final ReceiverRepository receiverRepository;

  public UserMessageListenerImpl(ProjectDataMapper mapper, ManagerRepository managerRepository,
      ReceiverRepository receiverRepository) {
    this.mapper = mapper;
    this.managerRepository = managerRepository;
    this.receiverRepository = receiverRepository;
  }

  @Override
  @Transactional
  public void createManager(ManagerRegisteredMessage message) {
    Manager manager = managerRepository.create(mapper.createManagerMessageToManager(message));
    if (manager == null) {
      throw new ProjectDomainException("Unable to persist manager id: " + message.getManagerId());
    }
  }

  @Override
  @Transactional
  public void createReceiver(ReceiverRegisteredMessage message) {
    Receiver receiver = receiverRepository.create(mapper.createReceiverMessageToReceiver(message));
    if (receiver == null) {
      throw new ProjectDomainException("Unable to persist receiver id: " + message.getReceiverId());
    }
  }

  @Override
  @Transactional
  public void updateReceiverBank(ReceiverProfileUpdatedMessage message) {
    Receiver receiver = receiverRepository
        .findById(new UserId(message.getReceiverId()))
        .orElseThrow(() -> new DataNotFoundException("Receiver: " + message.getReceiverId() + " not found"));

    receiver.changeBankAccount(message.getBankAccount());
    receiverRepository.save(receiver);
  }

}
