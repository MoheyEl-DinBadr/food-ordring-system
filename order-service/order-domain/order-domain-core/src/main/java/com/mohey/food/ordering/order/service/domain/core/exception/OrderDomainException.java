package com.mohey.food.ordering.order.service.domain.core.exception;

import com.mohey.food.ordering.system.common.exception.DomainException;

public class OrderDomainException extends DomainException {
    public OrderDomainException(String message) {
        super(message);
    }

    public OrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
