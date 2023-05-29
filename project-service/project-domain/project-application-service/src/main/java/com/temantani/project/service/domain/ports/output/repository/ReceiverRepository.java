package com.temantani.project.service.domain.ports.output.repository;

import java.util.List;
import java.util.Optional;

import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.domain.entity.Receiver;

public interface ReceiverRepository {

  Optional<List<Receiver>> findByIdIn(List<UserId> receiverIds);

  Optional<Receiver> findById(UserId receiverId);

  Receiver create(Receiver receiver);

  Receiver save(Receiver receiver);
}
