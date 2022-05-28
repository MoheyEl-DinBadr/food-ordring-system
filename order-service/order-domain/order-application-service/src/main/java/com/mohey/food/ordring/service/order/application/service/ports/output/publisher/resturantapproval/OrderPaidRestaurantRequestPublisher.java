package com.mohey.food.ordring.service.order.application.service.ports.output.publisher.resturantapproval;

import com.mohey.food.ordring.order.service.domain.core.event.OrderPaidEvent;
import com.mohey.food.ordring.system.common.event.publisher.DomainEventPublisher;

public interface OrderPaidRestaurantRequestPublisher extends DomainEventPublisher<OrderPaidEvent> {
}
