package com.temantani.project.service.domain.ports.output.publisher;

import com.temantani.domain.outbox.OutboxStatus;
import com.temantani.domain.ports.publisher.EventPublisher;
import com.temantani.project.service.domain.outbox.model.closefundraisingrequested.CloseFundraisingRequestedOutboxMessage;

public interface CloseFundraisingRequestedEventPublisher
        extends EventPublisher<CloseFundraisingRequestedOutboxMessage, OutboxStatus> {

}
