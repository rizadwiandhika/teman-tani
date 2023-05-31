package com.temantani.investment.service.domain.ports.output.repository;

import com.temantani.domain.outbox.OutboxRepository;
import com.temantani.investment.service.domain.outbox.model.fundraisingclosure.FundraisingClosureOutboxMessage;

public interface FundraisingClosureOutboxRepository extends OutboxRepository<FundraisingClosureOutboxMessage> {

}
