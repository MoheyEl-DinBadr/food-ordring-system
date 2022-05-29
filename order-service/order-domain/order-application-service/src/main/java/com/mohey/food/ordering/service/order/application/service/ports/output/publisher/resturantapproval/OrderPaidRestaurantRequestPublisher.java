package com.mohey.food.ordering.service.order.application.service.ports.output.publisher.resturantapproval;

import com.mohey.food.ordering.order.service.domain.core.event.OrderPaidEvent;
import com.mohey.food.ordering.system.common.event.publisher.DomainEventPublisher;

public interface OrderPaidRestaurantRequestPublisher extends DomainEventPublisher<OrderPaidEvent> {
}
