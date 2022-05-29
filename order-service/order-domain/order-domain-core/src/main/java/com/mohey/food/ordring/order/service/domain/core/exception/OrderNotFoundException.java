package com.mohey.food.ordring.order.service.domain.core.exception;

import com.mohey.food.ordring.system.common.exception.DomainException;

public class OrderNotFoundException extends DomainException {
    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
