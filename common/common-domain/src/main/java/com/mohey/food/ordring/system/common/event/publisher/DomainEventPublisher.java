package com.mohey.food.ordring.system.common.event.publisher;

import com.mohey.food.ordring.system.common.event.DomainEvent;

public interface DomainEventPublisher <T extends DomainEvent>{
    void publish(T domainEvent);
}
