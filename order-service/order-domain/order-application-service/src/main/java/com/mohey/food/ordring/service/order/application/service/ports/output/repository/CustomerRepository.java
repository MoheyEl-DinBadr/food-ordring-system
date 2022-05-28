package com.mohey.food.ordring.service.order.application.service.ports.output.repository;

import com.mohey.food.ordring.order.service.domain.core.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> findCustomer(UUID customerId);
}
