package com.mohey.food.ordring.order.service.domain.core.entity;

import com.mohey.food.ordring.order.service.domain.core.exception.OrderDomainException;
import com.mohey.food.ordring.order.service.domain.core.valueobject.OrderItemId;
import com.mohey.food.ordring.order.service.domain.core.valueobject.StreetAddress;
import com.mohey.food.ordring.order.service.domain.core.valueobject.TrackingId;
import com.mohey.food.ordring.system.common.entity.AggregateRoot;
import com.mohey.food.ordring.system.common.valueobject.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Order extends AggregateRoot<OrderId> {
    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAddress streetAddress;
    private final Money price;
    private final List<OrderItem> items;

    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    public void initializeOrder(){
        setId(new OrderId(UUID.randomUUID()));
        this.trackingId = new TrackingId(UUID.randomUUID());
        this.orderStatus = OrderStatus.PENDING;
        this.initializeOrderItems();
    }

    public void validateOrder(){
        this.validateInitialOrder();
        this.validateTotalPrice();
        this.validateItemsPrice();
    }

    public void pay(){
        if (!this.orderStatus.equals(OrderStatus.PENDING))
            throw new OrderDomainException("Order is not in correct state for pay operation!");
        this.orderStatus = OrderStatus.PAID;
    }

    public void approve(){
        if (!this.orderStatus.equals(OrderStatus.PAID))
            throw new OrderDomainException("Order is not in correct state for approve operation!");
        this.orderStatus = OrderStatus.APPROVED;
    }

    public void initCancel(List<String> failureMessages){
        if (!this.orderStatus.equals(OrderStatus.PAID))
            throw new OrderDomainException("Order is not in correct state for initCancel operation!");
        this.orderStatus = OrderStatus.CANCELLING;
        this.updateFailureMessages(failureMessages);
    }

    public void cancel(List<String> failureMessages){
        if (!(this.orderStatus.equals(OrderStatus.CANCELLING) || this.orderStatus.equals(OrderStatus.PENDING)))
            throw new OrderDomainException("Order is not in correct state for cancel operation!");
        this.orderStatus = OrderStatus.CANCELLED;
        this.updateFailureMessages(failureMessages);
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if (this.failureMessages != null && failureMessages != null)
            this.failureMessages.addAll(failureMessages.stream().filter(message -> !message.isEmpty()).toList());

        if (this.failureMessages == null)
            this.failureMessages = failureMessages.stream().filter(message -> !message.isEmpty()).collect(Collectors.toList());
    }

    private void validateItemsPrice() {
        Money orderItemsTotal = this.items.stream().map(orderItem -> {
            validateItemPrice(orderItem);
            return orderItem.getSubTotal();
        }).reduce(Money.ZERO, Money::add);

        if (!orderItemsTotal.equals(this.price)){
            throw new OrderDomainException("Total price: " + this.price.getAmount()
            + " is not equal to Order Items total: " + orderItemsTotal.getAmount());
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (!orderItem.isPriceValid()){
            throw new OrderDomainException("Order item price: " + orderItem.getPrice().getAmount() +
                    " is not valid for product: " + orderItem.getProduct().getId().getValue());
        }
    }

    private void validateTotalPrice() {
        if (this.price == null || !this.price.isGreaterThanZero()){
            throw new OrderDomainException("Total price must be greater than zero!");
        }
    }

    private void validateInitialOrder() {
        if (this.orderStatus != null || this.getId() != null){
            throw new OrderDomainException("Order is not in correct state for initialization!");
        }

    }

    private void initializeOrderItems() {
        long itemId = 1L;
        for (OrderItem item: this.items){
            item.initializeOrderItem(new OrderItemId(itemId++), super.getId());
        }
    }


    private Order(Builder builder) {
        super.setId(builder.orderId);
        customerId = builder.customerId;
        restaurantId = builder.restaurantId;
        streetAddress = builder.streetAddress;
        price = builder.price;
        items = builder.items;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessages = builder.failureMessages;
    }

    public static Builder builder() {
        return new Builder();
    }


    public CustomerId getCustomerId() {
        return customerId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public StreetAddress getStreetAddress() {
        return streetAddress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public static final class Builder {
        private OrderId orderId;
        private CustomerId customerId;
        private RestaurantId restaurantId;
        private StreetAddress streetAddress;
        private Money price;
        private List<OrderItem> items;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessages;

        private Builder() {
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder restaurantId(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder streetAddress(StreetAddress val) {
            streetAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
