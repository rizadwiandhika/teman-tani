package com.temantani.investment.service.domain.ports.output.repository;

import com.temantani.domain.outbox.OutboxRepository;
import com.temantani.investment.service.domain.outbox.model.investmentpaid.InvestmentPaidOutboxMessage;

public interface InvestmentPaidOutboxRepository extends OutboxRepository<InvestmentPaidOutboxMessage> {

}
