package com.temantani.project.service.dataaccess.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Component;

import com.temantani.domain.exception.DataAlreadyExistsException;
import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.dataaccess.mapper.ProjectDataAccessMapper;
import com.temantani.project.service.dataaccess.repository.ReceiverJpaRepository;
import com.temantani.project.service.dataaccess.util.ProjectDataAccessUtil;
import com.temantani.project.service.domain.entity.Receiver;
import com.temantani.project.service.domain.ports.output.repository.ReceiverRepository;

@Component
public class ReceiverRepositoryImpl implements ReceiverRepository {

  private final ReceiverJpaRepository repo;
  private final ProjectDataAccessMapper mapper;
  private final EntityManager manager;
  private final ProjectDataAccessUtil util;

  public ReceiverRepositoryImpl(ReceiverJpaRepository repo, ProjectDataAccessMapper mapper, EntityManager manager,
      ProjectDataAccessUtil util) {
    this.repo = repo;
    this.mapper = mapper;
    this.manager = manager;
    this.util = util;
  }

  @Override
  public Optional<List<Receiver>> findByIdIn(List<UserId> receiverIds) {
    List<Receiver> receivers = repo
        .findAllById(receiverIds.stream().map((id) -> id.getValue()).toList())
        .stream()
        .map(mapper::receiverEntityToReceiver)
        .collect(Collectors.toList());

    return Optional.of(receivers);
  }

  @Override
  public Optional<Receiver> findById(UserId receiverId) {
    return repo.findById(receiverId.getValue()).map(mapper::receiverEntityToReceiver);
  }

  @Override
  public Receiver save(Receiver receiver) {
    return mapper.receiverEntityToReceiver(repo.save(mapper.receiverToReceiverEntity(receiver)));
  }

  @Override
  public Receiver create(Receiver receiver) {
    try {
      manager.persist(mapper.receiverToReceiverEntity(receiver));
      manager.flush();

      return repo.findById(receiver.getId().getValue()).map(mapper::receiverEntityToReceiver).orElse(null);
    } catch (PersistenceException e) {
      if (util.isUniqueViolation(e)) {
        throw new DataAlreadyExistsException("Receiver already exists: " + receiver.getId().getValue(), e);
      }

      // if (e.getCause() instanceof ConstraintViolationException) {
      // throw new DataAlreadyExistsException("Receiver already exists: " +
      // receiver.getId().getValue(), e);
      // }
      throw e;
    }
  }

}
