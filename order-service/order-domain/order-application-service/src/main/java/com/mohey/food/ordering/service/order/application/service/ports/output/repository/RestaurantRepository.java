package com.mohey.food.ordering.service.order.application.service.ports.output.repository;

import com.mohey.food.ordering.order.service.domain.core.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {
    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
