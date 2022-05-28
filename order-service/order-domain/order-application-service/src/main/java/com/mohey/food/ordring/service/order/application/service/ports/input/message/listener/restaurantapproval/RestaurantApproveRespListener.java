package com.mohey.food.ordring.service.order.application.service.ports.input.message.listener.restaurantapproval;

import com.mohey.food.ordring.service.order.application.service.dto.message.RestaurantApprovalResponse;

public interface RestaurantApproveRespListener {
    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);
    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
