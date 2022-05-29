package com.mohey.food.ordering.service.order.application.service;

import com.mohey.food.ordering.service.order.application.service.dto.message.RestaurantApprovalResponse;
import com.mohey.food.ordering.service.order.application.service.ports.input.message.listener.restaurantapproval.RstaurantApproveRespListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Slf4j
@Service
@Validated
public class RstaurantApproveRespListenerImpl implements RstaurantApproveRespListener {
    @Override
    public void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse) {

    }

    @Override
    public void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse) {

    }
}
