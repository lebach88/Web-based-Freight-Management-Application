package com.dev.backendspringboot.service;

import com.dev.backendspringboot.api.dto.request.OrderDetailRequest;
import com.dev.backendspringboot.api.dto.response.MessageApi;
import com.dev.backendspringboot.api.dto.response.OrderDetailDto;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetailDto> getAllOrderDetails();

    OrderDetailDto getOrderDetailById(String orderDetailId);

    OrderDetailDto createOrderDetail(OrderDetailRequest orderDetailRequest);

    OrderDetailDto updateOrderDetail(String orderDetailId, OrderDetailRequest orderDetailRequest);

    MessageApi deleteOrderDetail(String orderDetailId);

    MessageApi deleteAllOrderDetail();
}
