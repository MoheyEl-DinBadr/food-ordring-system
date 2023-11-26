package com.mohey.food.ordering.system.common.event.publisher;

import com.mohey.food.ordering.system.common.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {
    void publish(T domainEvent);
}
