package com.mohey.food.ordering.order.service.domain.core.exception;

import com.mohey.food.ordering.system.common.exception.DomainException;

public class OrderNotFoundException extends DomainException {
    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
