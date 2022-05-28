package com.mohey.food.ordring.service.order.application.service;

import com.mohey.food.ordring.order.service.domain.core.event.OrderCreatedEvent;
import com.mohey.food.ordring.system.common.event.publisher.DomainEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationDomainEventPublisher implements /*ApplicationEventPublisherAware,*/ DomainEventPublisher<OrderCreatedEvent> {

    private final ApplicationEventPublisher applicationEventPublisher;

    public ApplicationDomainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /*private ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }*/

    @Override
    public void publish(OrderCreatedEvent domainEvent) {
        this.applicationEventPublisher.publishEvent(domainEvent);
        log.info("OrderCreatedEvent is published for order with id: {}", domainEvent.getOrder().getId().getValue());

    }
}
