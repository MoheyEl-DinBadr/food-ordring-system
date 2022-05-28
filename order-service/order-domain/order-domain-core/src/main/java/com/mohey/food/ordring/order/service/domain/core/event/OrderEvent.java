package com.mohey.food.ordring.order.service.domain.core.event;

import com.mohey.food.ordring.order.service.domain.core.entity.Order;
import com.mohey.food.ordring.system.common.event.DomainEvent;

import java.time.ZonedDateTime;

public abstract class OrderEvent implements DomainEvent<Order> {
    private final Order order;
    private final ZonedDateTime createdAt;

    public OrderEvent(Order order, ZonedDateTime createdAt) {
        this.order = order;
        this.createdAt = createdAt;
    }

    public Order getOrder() {
        return order;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
