package com.mohey.food.ordering.order.service.domain.core.event;

import com.mohey.food.ordering.order.service.domain.core.entity.Order;

import java.time.ZonedDateTime;

public class OrderCreatedEvent extends OrderEvent{
    public OrderCreatedEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
