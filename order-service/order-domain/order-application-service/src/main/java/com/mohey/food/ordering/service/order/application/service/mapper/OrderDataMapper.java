package com.mohey.food.ordering.service.order.application.service.mapper;

import com.mohey.food.ordering.service.order.application.service.dto.create.CreateOrderCommand;
import com.mohey.food.ordering.service.order.application.service.dto.create.CreateOrderResponse;
import com.mohey.food.ordering.service.order.application.service.dto.create.OrderAddress;
import com.mohey.food.ordering.service.order.application.service.dto.track.TrackOrderResponse;
import com.mohey.food.ordering.order.service.domain.core.entity.Order;
import com.mohey.food.ordering.order.service.domain.core.entity.OrderItem;
import com.mohey.food.ordering.order.service.domain.core.entity.Product;
import com.mohey.food.ordering.order.service.domain.core.entity.Restaurant;
import com.mohey.food.ordering.order.service.domain.core.valueobject.StreetAddress;
import com.mohey.food.ordering.system.common.valueobject.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        return Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getItems().stream().map(orderItem ->
                                new Product(new ProductId(orderItem.getProductId()))
                        ).collect(Collectors.toList())
                )

                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .streetAddress(this.orderAddressToStreetAddress(createOrderCommand.getOrderAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(this.orderItemsToOrderItemEntities(createOrderCommand.getItems()))
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemEntities(
            List<com.mohey.food.ordering.service.order.application.service.dto.create.OrderItem> orderItems) {
        return orderItems.stream().map(orderItem ->
                        OrderItem.builder()
                                .product(new Product(new ProductId(orderItem.getProductId())))
                                .price(new Money(orderItem.getPrice()))
                                .quantity(orderItem.getQuantity())
                                .subTotal(new Money(orderItem.getSubTotal()))
                                .build()
                )
                .collect(Collectors.toList());
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.getStreet(),
                orderAddress.getPostalCode(),
                orderAddress.getCity()
        );
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order orderResult, String message) {
        return CreateOrderResponse.builder()
                .orderTrackingId(orderResult.getTrackingId().getValue())
                .orderStatus(orderResult.getOrderStatus())
                .message(message)
                .build();
    }

    public TrackOrderResponse orderToTrackOrderResponse(Order order){
        return TrackOrderResponse.builder()
                .orderStatus(order.getOrderStatus())
                .orderTrackingId(order.getTrackingId().getValue())
                .failureMessages(order.getFailureMessages())
                .build();
    }
}
