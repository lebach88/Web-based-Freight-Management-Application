package com.dev.backendspringboot.service;

import com.dev.backendspringboot.api.dto.request.OrderRequest;
import com.dev.backendspringboot.api.dto.response.MessageApi;
import com.dev.backendspringboot.api.dto.response.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrders();

    OrderDto getOrderById(String orderId);

    OrderDto createOrder(OrderRequest orderRequest);

    OrderDto updateOrder(String orderId, OrderRequest order);

    MessageApi deleteOrder(String orderId);

    MessageApi deleteAllOrder();
}
