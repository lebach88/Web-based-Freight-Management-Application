package com.dev.backendspringboot.service.util;

import com.dev.backendspringboot.api.dto.request.OrderDetailRequest;
import com.dev.backendspringboot.api.dto.response.OrderDetailDto;
import com.dev.backendspringboot.document.OrderDetail;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailUtil {
    public OrderDetailDto mapOrderDetailToOrderDetailDto(OrderDetail orderDetail){
        return OrderDetailDto.builder()
                .id(orderDetail.getId())
                .price(orderDetail.getPrice())
                .quantity(orderDetail.getQuantity())
                .build();
    }
    public OrderDetail mapOrderDetailRequestToOrderDetail(OrderDetailRequest orderDetailRequest){
        OrderDetail orderDetail = OrderDetail.builder()
                .price(orderDetailRequest.getPrice())
                .quantity(orderDetailRequest.getQuantity())
                .build();
        return orderDetail;
    }
}
