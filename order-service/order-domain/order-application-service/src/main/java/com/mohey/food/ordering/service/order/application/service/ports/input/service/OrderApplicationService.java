package com.mohey.food.ordering.service.order.application.service.ports.input.service;

import com.mohey.food.ordering.service.order.application.service.dto.create.CreateOrderCommand;
import com.mohey.food.ordering.service.order.application.service.dto.create.CreateOrderResponse;
import com.mohey.food.ordering.service.order.application.service.dto.track.TrackOrderQuery;
import com.mohey.food.ordering.service.order.application.service.dto.track.TrackOrderResponse;
import jakarta.validation.Valid;


public interface OrderApplicationService {
    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);
    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
