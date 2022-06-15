package com.mohey.food.ordering.service.order.application.service;

import com.mohey.food.ordering.service.order.application.service.dto.create.CreateOrderCommand;
import com.mohey.food.ordering.service.order.application.service.mapper.OrderDataMapper;
import com.mohey.food.ordering.service.order.application.service.ports.output.repository.CustomerRepository;
import com.mohey.food.ordering.service.order.application.service.ports.output.repository.OrderRepository;
import com.mohey.food.ordering.service.order.application.service.ports.output.repository.RestaurantRepository;
import com.mohey.food.ordering.order.service.domain.core.OrderDomainService;
import com.mohey.food.ordering.order.service.domain.core.entity.Order;
import com.mohey.food.ordering.order.service.domain.core.entity.Restaurant;
import com.mohey.food.ordering.order.service.domain.core.event.OrderCreatedEvent;
import com.mohey.food.ordering.order.service.domain.core.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
public class OrderCreateHelper {

    private final OrderRepository orderRepository;
    private final OrderDataMapper orderDataMapper;
    private final CustomerRepository customerRepository;
    private final OrderDomainService orderDomainService;
    private final RestaurantRepository restaurantRepository;

    public OrderCreateHelper(OrderDomainService orderDomainService,
                             OrderRepository orderRepository,
                             CustomerRepository customerRepository,
                             RestaurantRepository restaurantRepository,
                             OrderDataMapper orderDataMapper) {
        this.orderRepository = orderRepository;
        this.orderDataMapper = orderDataMapper;
        this.customerRepository = customerRepository;
        this.orderDomainService = orderDomainService;
        this.restaurantRepository = restaurantRepository;
    }


    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand){
        this.checkCustomer(createOrderCommand.getCustomerId());
        Restaurant restaurant = this.checkRestaurant(createOrderCommand);
        var order = this.orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        var orderCreatedEvent = this.orderDomainService.validateAndInitiateOrder(order, restaurant);
        this.saveOrder(order);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        return orderCreatedEvent;
    }


    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        var restaurantOptional = this.restaurantRepository.findRestaurantInformation(
                orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)
        );
        if (restaurantOptional.isEmpty()) {
            log.warn("Could not find restaurant with restaurant id: {}", createOrderCommand.getRestaurantId());
            throw new OrderDomainException("Could not find restaurant with restaurant id: " + createOrderCommand.getRestaurantId());
        }
        return restaurantOptional.get();
    }

    private void checkCustomer(UUID customerId) {
        var customer = this.customerRepository.findCustomer(customerId);
        if (customer.isEmpty()) {
            log.warn("Could not find Customer with id: {}", customerId);
            throw new OrderDomainException("Could not find Customer with id: " + customerId);
        }
    }

    private Order saveOrder(Order order) {
        var orderResult = this.orderRepository.save(order);
        if (orderResult == null) {
            log.error("Could not save order!");
            throw new OrderDomainException("Could not save order!");
        }
        log.info("Order is saved with id: {}", orderResult.getId().getValue());

        return orderResult;
    }
}
