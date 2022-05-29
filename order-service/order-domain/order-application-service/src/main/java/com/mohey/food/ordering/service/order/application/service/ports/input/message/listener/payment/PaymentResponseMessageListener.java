package com.mohey.food.ordering.service.order.application.service.ports.input.message.listener.payment;

import com.mohey.food.ordering.service.order.application.service.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {
    void paymentCompleted(PaymentResponse paymentResponse);
    void paymentCancelled(PaymentResponse paymentResponse);
}
