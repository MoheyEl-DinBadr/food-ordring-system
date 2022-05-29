package com.mohey.food.ordering.service.order.application.service.ports.output.publisher.payment;

import com.mohey.food.ordering.order.service.domain.core.event.OrderCreatedEvent;
import com.mohey.food.ordering.system.common.event.publisher.DomainEventPublisher;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
