package com.mohey.food.ordring.service.order.application.service.ports.output.publisher.payment;

import com.mohey.food.ordring.order.service.domain.core.event.OrderCancelledEvent;
import com.mohey.food.ordring.system.common.event.publisher.DomainEventPublisher;

public interface OrderCancelledPaymentMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}
