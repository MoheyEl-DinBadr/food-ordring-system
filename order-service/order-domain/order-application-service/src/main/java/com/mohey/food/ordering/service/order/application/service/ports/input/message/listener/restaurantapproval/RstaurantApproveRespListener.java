package com.mohey.food.ordering.service.order.application.service.ports.input.message.listener.restaurantapproval;

import com.mohey.food.ordering.service.order.application.service.dto.message.RestaurantApprovalResponse;

public interface RstaurantApproveRespListener {
    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);

    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
