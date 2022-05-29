package com.mohey.food.ordring.service.order.application.service;

import com.mohey.food.ordring.service.order.application.service.dto.message.RestaurantApprovalResponse;
import com.mohey.food.ordring.service.order.application.service.ports.input.message.listener.restaurantapproval.RestaurantApproveRespListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Slf4j
@Service
@Validated
public class RestaurantApproveRespListenerImpl implements RestaurantApproveRespListener {
    @Override
    public void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse) {

    }

    @Override
    public void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse) {

    }
}
