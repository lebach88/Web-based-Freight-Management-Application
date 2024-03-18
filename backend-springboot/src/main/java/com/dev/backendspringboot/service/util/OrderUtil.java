package com.dev.backendspringboot.service.util;

import com.dev.backendspringboot.api.dto.request.OrderRequest;
import com.dev.backendspringboot.api.dto.response.OrderDto;
import com.dev.backendspringboot.document.Order;
import com.dev.backendspringboot.document.OrderDetail;
import com.dev.backendspringboot.repository.OrderDetailRepository;
import com.dev.backendspringboot.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderUtil {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    public OrderDto mapOrderToOrderDto(Order order){

        return OrderDto.builder()
                .id(order.getId())
                .total(order.getTotal())
                .details(order.getDetails())
                .createdAt(order.getCreatedAt())
                .updatedOn(order.getUpdatedOn())
                .build();
    }
    public List<OrderDetail> getSaveOrderDetails(OrderRequest orderRequest) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        if (orderRequest.getDetails() != null){
            for (OrderDetail item : orderRequest.getDetails()){
                orderDetails.add(orderDetailRepository.save(item));
            }
        }
        return orderDetails;
    }

}
