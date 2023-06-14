package com.temantani.project.service.domain.ports.output.repository;

import com.temantani.domain.outbox.OutboxRepository;
import com.temantani.project.service.domain.outbox.model.fundraisingregistered.FundraisingRegisteredOutboxMessage;

public interface FundraisingRegisteredOutboxRepository extends OutboxRepository<FundraisingRegisteredOutboxMessage> {

}
