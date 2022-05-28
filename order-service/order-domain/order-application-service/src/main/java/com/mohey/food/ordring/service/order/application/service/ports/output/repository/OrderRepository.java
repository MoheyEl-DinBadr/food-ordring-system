package com.mohey.food.ordring.service.order.application.service.ports.output.repository;

import com.mohey.food.ordring.order.service.domain.core.entity.Order;
import com.mohey.food.ordring.order.service.domain.core.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findByTrackingId(TrackingId trackingId);

}
