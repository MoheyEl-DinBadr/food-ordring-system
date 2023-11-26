package com.mohey.food.ordering.service.order.application.service;

import com.mohey.food.ordering.service.order.application.service.dto.create.CreateOrderCommand;
import com.mohey.food.ordering.service.order.application.service.dto.create.CreateOrderResponse;
import com.mohey.food.ordering.service.order.application.service.mapper.OrderDataMapper;
import com.mohey.food.ordering.service.order.application.service.ports.output.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderCreateCommandHandler {

    private final OrderCreateHelper orderCreateHelper;

    private final OrderDataMapper orderDataMapper;
    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

    public OrderCreateCommandHandler(OrderDataMapper orderDataMapper,
                                     OrderCreateHelper orderCreateHelper,
                                     OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher) {

        this.orderDataMapper = orderDataMapper;
        this.orderCreateHelper = orderCreateHelper;
        this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
    }

    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {

        var orderCreatedEvent = this.orderCreateHelper.persistOrder(createOrderCommand);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        this.orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
        return this.orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder(), "Order has been created successfully");
    }
}
