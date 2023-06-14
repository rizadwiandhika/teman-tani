package com.temantani.project.service.domain.ports.output.repository;

import com.temantani.domain.outbox.OutboxRepository;
import com.temantani.project.service.domain.outbox.model.closefundraisingrequested.CloseFundraisingRequestedOutboxMessage;

public interface CloseFundraisingRequestedOutboxRepository
        extends OutboxRepository<CloseFundraisingRequestedOutboxMessage> {

}
