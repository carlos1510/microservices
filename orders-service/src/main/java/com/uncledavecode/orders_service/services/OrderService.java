package com.uncledavecode.orders_service.services;

import com.uncledavecode.orders_service.model.dtos.OrderItemRequest;
import com.uncledavecode.orders_service.model.dtos.OrderRequest;
import com.uncledavecode.orders_service.model.entities.Order;
import com.uncledavecode.orders_service.model.entities.OrderItems;
import com.uncledavecode.orders_service.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){

        //Check for inventary

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderItems(orderRequest.getOrderItems().stream()
                .map(orderItemRequest -> mapOrderItemRequestToOrderItem(orderItemRequest, order))
                .toList());
        this.orderRepository.save(order);
    }

    private OrderItems mapOrderItemRequestToOrderItem(OrderItemRequest orderItemRequest, Order order) {
        return  OrderItems.builder()
                .id(orderItemRequest.getId())
                .sku(orderItemRequest.getSku())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .order(order)
                .build();
    }
}
