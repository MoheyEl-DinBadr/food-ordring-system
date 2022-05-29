package com.mohey.food.ordering.service.order.application.service.ports.output.repository;

import com.mohey.food.ordering.order.service.domain.core.entity.Order;
import com.mohey.food.ordering.order.service.domain.core.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findByTrackingId(TrackingId trackingId);

}
