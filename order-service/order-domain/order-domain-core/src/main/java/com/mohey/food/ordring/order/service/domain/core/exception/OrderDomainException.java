package com.mohey.food.ordring.order.service.domain.core.exception;

import com.mohey.food.ordring.system.common.exception.DomainException;

public class OrderDomainException extends DomainException {
    public OrderDomainException(String message) {
        super(message);
    }

    public OrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
