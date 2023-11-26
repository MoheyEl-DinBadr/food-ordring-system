package com.mohey.food.ordering.order.service.domain.core.entity;

import com.mohey.food.ordering.order.service.domain.core.valueobject.OrderItemId;
import com.mohey.food.ordering.system.common.entity.BaseEntity;
import com.mohey.food.ordering.system.common.valueobject.Money;
import com.mohey.food.ordering.system.common.valueobject.OrderId;

public class OrderItem extends BaseEntity<OrderItemId> {
    private final Product product;
    private final int quantity;
    private final Money price;
    private final Money subTotal;
    private OrderId orderId;

    private OrderItem(Builder builder) {
        super.setId(builder.orderItemId);
        product = builder.product;
        quantity = builder.quantity;
        price = builder.price;
        subTotal = builder.subTotal;
    }

    public static Builder builder() {
        return new Builder();
    }

    void initializeOrderItem(OrderItemId orderItemId, OrderId orderId) {
        super.setId(orderItemId);
        this.orderId = orderId;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getPrice() {
        return price;
    }

    public Money getSubTotal() {
        return subTotal;
    }

    public boolean isPriceValid() {
        return this.price.isGreaterThanZero() &&
                this.price.equals(this.product.getPrice()) &&
                this.price.multiply(this.quantity).equals(this.subTotal);
    }

    public static final class Builder {
        private OrderItemId orderItemId;
        private Product product;
        private int quantity;
        private Money price;
        private Money subTotal;

        private Builder() {
        }

        public Builder orderItemId(OrderItemId val) {
            orderItemId = val;
            return this;
        }

        public Builder product(Product val) {
            product = val;
            return this;
        }

        public Builder quantity(int val) {
            quantity = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder subTotal(Money val) {
            subTotal = val;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
