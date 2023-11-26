package com.mohey.food.ordering.service.order.application.service;


import com.mohey.food.ordering.order.service.domain.core.entity.Customer;
import com.mohey.food.ordering.order.service.domain.core.entity.Order;
import com.mohey.food.ordering.order.service.domain.core.entity.Product;
import com.mohey.food.ordering.order.service.domain.core.entity.Restaurant;
import com.mohey.food.ordering.order.service.domain.core.exception.OrderDomainException;
import com.mohey.food.ordering.service.order.application.service.dto.create.CreateOrderCommand;
import com.mohey.food.ordering.service.order.application.service.dto.create.CreateOrderResponse;
import com.mohey.food.ordering.service.order.application.service.dto.create.OrderAddress;
import com.mohey.food.ordering.service.order.application.service.dto.create.OrderItem;
import com.mohey.food.ordering.service.order.application.service.mapper.OrderDataMapper;
import com.mohey.food.ordering.service.order.application.service.ports.input.service.OrderApplicationService;
import com.mohey.food.ordering.service.order.application.service.ports.output.repository.CustomerRepository;
import com.mohey.food.ordering.service.order.application.service.ports.output.repository.OrderRepository;
import com.mohey.food.ordering.service.order.application.service.ports.output.repository.RestaurantRepository;
import com.mohey.food.ordering.system.common.valueobject.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {

    private final UUID CUSTOMER_ID = UUID.fromString("3328a66e-8e71-4c9d-a58e-a33737cceb9d");
    private final UUID RESTAURANT_ID = UUID.fromString("2e955f4c-5509-42d8-9929-582b1bd864cc");
    private final UUID PRODUCT_ID = UUID.fromString("5616a036-d7d7-4fa7-a770-a6eb83691c09");
    private final UUID PRODUCT_ID2 = UUID.fromString("fbe3bea3-1c1f-4793-9e54-7707933e1f50");
    private final UUID ORDER_ID = UUID.fromString("cf51a50e-d5ae-4448-a98e-5999217400c6");
    private final BigDecimal PRICE = new BigDecimal("200.00");
    @Autowired
    private OrderApplicationService orderApplicationService;
    @Autowired
    private OrderDataMapper orderDataMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private CustomerRepository customerRepository;
    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandWrongPrice;
    private CreateOrderCommand createOrderCommandWrongProductPrice;

    @BeforeAll
    public void init() {

        createOrderCommand = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .orderAddress(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(PRICE)
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID2)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()
                ))
                .build();

        createOrderCommandWrongPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .orderAddress(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("250.00"))
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID2)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()
                ))
                .build();

        createOrderCommandWrongProductPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .orderAddress(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("210.00"))
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("60.00"))
                                .subTotal(new BigDecimal("60.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID2)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()
                ))
                .build();

        Customer customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));

        Restaurant restaurant = Restaurant
                .builder()
                .restaurantId(new RestaurantId(createOrderCommand.restaurantId()))
                .products(List.of(new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(PRODUCT_ID2), "product-2", new Money(new BigDecimal("50.00")))))
                .active(true)
                .build();

        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));

        when(customerRepository.findCustomer(CUSTOMER_ID))
                .thenReturn(Optional.of(customer));

        when(restaurantRepository.findRestaurantInformation(restaurant))
                .thenReturn(Optional.of(restaurant));

        when(orderRepository.save(any(Order.class)))
                .thenReturn(order);

    }

    @Test
    public void testCreateOrder() {
        CreateOrderResponse createOrderResponse = this.orderApplicationService.createOrder(createOrderCommand);
        assertEquals(OrderStatus.PENDING, createOrderResponse.getOrderStatus());
        assertEquals("Order has been created successfully", createOrderResponse.getMessage());
        assertNotNull(createOrderResponse.getOrderTrackingId());
    }

    @Test
    public void testCreateOrderWithWrongTotalPrice() {
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> this.orderApplicationService.createOrder(createOrderCommandWrongPrice));
        assertEquals("Total price: 250.00 is not equal to Order Items total: 200.00",
                orderDomainException.getMessage());
    }

    @Test
    public void testCreateOrderWithWrongProductPrice() {
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
                () -> this.orderApplicationService.createOrder(createOrderCommandWrongProductPrice));
        assertEquals("Order item price: 60.00 is not valid for product: 5616a036-d7d7-4fa7-a770-a6eb83691c09",
                orderDomainException.getMessage());
    }

    @Test
    public void testCreateOrderWithPassiveRestaurant() {
        var restaurant = Restaurant
                .builder()
                .restaurantId(new RestaurantId(createOrderCommand.restaurantId()))
                .products(List.of(new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(PRODUCT_ID2), "product-2", new Money(new BigDecimal("50.00")))))
                .active(false)
                .build();

        when(restaurantRepository.findRestaurantInformation(restaurant))
                .thenReturn(Optional.of(restaurant));

        var orderDomainException = assertThrows(OrderDomainException.class, () -> this.orderApplicationService.createOrder(createOrderCommand));

        assertEquals("Restaurant with id: 2e955f4c-5509-42d8-9929-582b1bd864cc is not active!",
                orderDomainException.getMessage());
    }

}
