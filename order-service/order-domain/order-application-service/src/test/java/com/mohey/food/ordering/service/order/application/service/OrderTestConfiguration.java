package com.mohey.food.ordering.service.order.application.service;

import com.mohey.food.ordering.order.service.domain.core.OrderDomainService;
import com.mohey.food.ordering.order.service.domain.core.OrderDomainServiceImpl;
import com.mohey.food.ordering.service.order.application.service.ports.output.publisher.payment.OrderCancelledPaymentMessagePublisher;
import com.mohey.food.ordering.service.order.application.service.ports.output.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.mohey.food.ordering.service.order.application.service.ports.output.publisher.resturantapproval.OrderPaidRestaurantRequestPublisher;
import com.mohey.food.ordering.service.order.application.service.ports.output.repository.CustomerRepository;
import com.mohey.food.ordering.service.order.application.service.ports.output.repository.OrderRepository;
import com.mohey.food.ordering.service.order.application.service.ports.output.repository.RestaurantRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.mohey.food.ordering")
public class OrderTestConfiguration {


    @Bean
    public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher.class);
    }

    @Bean
    public OrderCancelledPaymentMessagePublisher orderCancelledPaymentMessagePublisher() {
        return Mockito.mock(OrderCancelledPaymentMessagePublisher.class);
    }

    @Bean
    public OrderPaidRestaurantRequestPublisher orderPaidRestaurantRequestPublisher() {
        return Mockito.mock(OrderPaidRestaurantRequestPublisher.class);
    }

    @Bean
    public OrderRepository orderRepository() {
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public CustomerRepository customerRepository() {
        return Mockito.mock(CustomerRepository.class);
    }

    @Bean
    public RestaurantRepository restaurantRepository() {
        return Mockito.mock(RestaurantRepository.class);
    }

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }
}
