package com.mohey.food.ordring.service.order.application.service;

import com.mohey.food.ordring.order.service.domain.core.exception.OrderNotFoundException;
import com.mohey.food.ordring.order.service.domain.core.valueobject.TrackingId;
import com.mohey.food.ordring.service.order.application.service.dto.track.TrackOrderQuery;
import com.mohey.food.ordring.service.order.application.service.dto.track.TrackOrderResponse;
import com.mohey.food.ordring.service.order.application.service.mapper.OrderDataMapper;
import com.mohey.food.ordring.service.order.application.service.ports.output.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class OrderTrackCommandHandler {

    private final OrderDataMapper orderDataMapper;
    private final OrderRepository orderRepository;

    public OrderTrackCommandHandler(OrderDataMapper orderDataMapper, OrderRepository orderRepository) {
        this.orderDataMapper = orderDataMapper;
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        var optionalOrder = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()));
        if (optionalOrder.isEmpty()){
            log.warn("Could not find order with tracking id: {}", trackOrderQuery.getOrderTrackingId());
            throw new OrderNotFoundException("Could not find order with tracking id: " + trackOrderQuery.getOrderTrackingId());
        }
        return this.orderDataMapper.orderToTrackOrderResponse(optionalOrder.get());
    }
}
