package com.mohey.food.ordring.service.order.application.service.ports.output.repository;

import com.mohey.food.ordring.order.service.domain.core.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {
    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
