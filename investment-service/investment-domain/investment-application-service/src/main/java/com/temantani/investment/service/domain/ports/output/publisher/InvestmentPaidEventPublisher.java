package com.temantani.investment.service.domain.ports.output.publisher;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.domain.ports.publisher.EventPublisher;
import com.temantani.investment.service.domain.outbox.model.InvestmentPaidOutboxMessage;

public interface InvestmentPaidEventPublisher extends EventPublisher<InvestmentPaidOutboxMessage, OutboxStatus> {

}
