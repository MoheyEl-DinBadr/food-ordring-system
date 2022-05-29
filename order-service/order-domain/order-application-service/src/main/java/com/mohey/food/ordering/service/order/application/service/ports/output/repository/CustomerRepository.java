package com.mohey.food.ordering.service.order.application.service.ports.output.repository;

import com.mohey.food.ordering.order.service.domain.core.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> findCustomer(UUID customerId);
}
