package com.mohey.food.ordring.order.service.domain.core;

import com.mohey.food.ordring.order.service.domain.core.entity.Order;
import com.mohey.food.ordring.order.service.domain.core.entity.Restaurant;
import com.mohey.food.ordring.order.service.domain.core.event.OrderCancelledEvent;
import com.mohey.food.ordring.order.service.domain.core.event.OrderCreatedEvent;
import com.mohey.food.ordring.order.service.domain.core.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);
    OrderPaidEvent payOrder(Order order);
    void approveOrder(Order order);
    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);
    void cancelOrder(Order order, List<String> failureMessages);
}
