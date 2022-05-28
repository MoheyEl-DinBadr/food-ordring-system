package com.mohey.food.ordring.service.order.application.service;

import com.mohey.food.ordring.service.order.application.service.dto.create.CreateOrderCommand;
import com.mohey.food.ordring.service.order.application.service.dto.create.CreateOrderResponse;
import com.mohey.food.ordring.service.order.application.service.dto.track.TrackOrderQuery;
import com.mohey.food.ordring.service.order.application.service.dto.track.TrackOrderResponse;
import com.mohey.food.ordring.service.order.application.service.ports.input.service.OrderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
class OrderApplicationServiceImpl implements OrderApplicationService {

    private final OrderCreateCommandHandler orderCreateCommandHandler;
    private final OrderTrackCommandHandler orderTrackCommandHandler;

    OrderApplicationServiceImpl(OrderCreateCommandHandler orderCreateCommandHandler,
                                OrderTrackCommandHandler orderTrackCommandHandler) {
        this.orderCreateCommandHandler = orderCreateCommandHandler;
        this.orderTrackCommandHandler = orderTrackCommandHandler;
    }


    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return this.orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return this.orderTrackCommandHandler.trackOrder(trackOrderQuery);
    }
}
