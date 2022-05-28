package com.mohey.food.ordring.order.service.domain.core;

import com.mohey.food.ordring.order.service.domain.core.entity.Order;
import com.mohey.food.ordring.order.service.domain.core.entity.OrderItem;
import com.mohey.food.ordring.order.service.domain.core.entity.Restaurant;
import com.mohey.food.ordring.order.service.domain.core.event.OrderCancelledEvent;
import com.mohey.food.ordring.order.service.domain.core.event.OrderCreatedEvent;
import com.mohey.food.ordring.order.service.domain.core.event.OrderPaidEvent;
import com.mohey.food.ordring.order.service.domain.core.exception.OrderDomainException;
import com.mohey.food.ordring.system.common.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService{

    Supplier<ZonedDateTime> retrieveZonedDateTime = () -> ZonedDateTime.now(ZoneId.of("UTC"));

    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
        this.validateRestaurant(restaurant);
        this.setOrderProductInformation(order, restaurant);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id: {} is initiated", order.getId().getValue());
        return new OrderCreatedEvent(order, this.retrieveZonedDateTime.get());
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order with id: {} is paid", order.getId().getValue());
        return new OrderPaidEvent(order, this.retrieveZonedDateTime.get());
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id: {} has been approved", order.getId().getValue());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.initCancel(failureMessages);
        log.info("Order payment is cancelling for order id: {}", order.getId().getValue());
        return new OrderCancelledEvent(order, this.retrieveZonedDateTime.get());
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order with id: {} has been cancelled", order.getId().getValue());
    }


    private void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.isActive())
            throw new OrderDomainException("Restaurant with id: " + restaurant.getId().getValue() +
                    " is not active!");
    }

    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        var orderItemProductMap = order.getItems().stream()
                .map(OrderItem::getProduct)
                .collect(Collectors.toMap(BaseEntity::getId, Function.identity()));
        var restaurantProductsMap = restaurant.getProducts().stream()
                .collect(Collectors.toMap(BaseEntity::getId, Function.identity()));

        orderItemProductMap.forEach((productId, product) -> {
            var restaurantProduct = restaurantProductsMap.get(productId);
            product.updateWithConfirmedNameAndPrice(restaurantProduct.getName(), restaurantProduct.getPrice());
        });
    }
}
