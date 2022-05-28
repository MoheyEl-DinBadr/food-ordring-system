package com.mohey.food.ordring.order.service.domain.core.entity;

import com.mohey.food.ordring.system.common.entity.BaseEntity;
import com.mohey.food.ordring.system.common.valueobject.Money;
import com.mohey.food.ordring.system.common.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productId) {
        super.setId(productId);
    }

    public Product(ProductId productId, String name, Money price) {
        super.setId(productId);
        this.name = name;
        this.price = price;
    }

    public void updateWithConfirmedNameAndPrice(String name, Money price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }
}
