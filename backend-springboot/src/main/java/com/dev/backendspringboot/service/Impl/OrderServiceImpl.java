package com.dev.backendspringboot.service.Impl;

import com.dev.backendspringboot.api.dto.request.OrderRequest;
import com.dev.backendspringboot.api.dto.response.MessageApi;
import com.dev.backendspringboot.api.dto.response.OrderDto;
import com.dev.backendspringboot.api.error.OrderNotFoundException;
import com.dev.backendspringboot.api.error.ServerErrorException;
import com.dev.backendspringboot.document.Order;
import com.dev.backendspringboot.document.OrderDetail;
import com.dev.backendspringboot.repository.OrderDetailRepository;
import com.dev.backendspringboot.repository.OrderRepository;
import com.dev.backendspringboot.service.OrderService;
import com.dev.backendspringboot.service.Time;
import com.dev.backendspringboot.service.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private OrderUtil orderUtil;
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, OrderUtil orderUtil) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.orderUtil = orderUtil;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        try {
            List<Order> orders = orderRepository.findAll();
            return orders.stream().map(order -> orderUtil.mapOrderToOrderDto(order)).collect(Collectors.toList());
        } catch (ServerErrorException e) {
            throw new ServerErrorException("Đã xảy ra lỗi khi lấy tất cả order ",e);
        }
    }

    @Override
    public OrderDto getOrderById(String orderId) {
        try {
            Order order = orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException("order not found by id: "+ orderId));
            return orderUtil.mapOrderToOrderDto(order);
        } catch (ServerErrorException e) {
            throw new ServerErrorException("Đã xảy ra lỗi khi lấy tất cả order ",e);
        }
    }

    @Override
    public OrderDto createOrder(OrderRequest orderRequest) {
        try {
            Order order = Order.builder()
                    .total(orderRequest.getTotal())
                    .createdAt(Time.NOW.toLocalDateTime())
                    .build();
            Order newOrder = orderRepository.save(order);
            List<OrderDetail> orderDetails = orderUtil.getSaveOrderDetails(orderRequest);
            newOrder.setDetails(orderDetails);
            return orderUtil.mapOrderToOrderDto(orderRepository.save(newOrder));
        } catch (ServerErrorException e) {
            throw new ServerErrorException("Đã xảy ra lỗi khi lấy tất cả order ",e);
        }
    }

    @Override
    public OrderDto updateOrder(String orderId, OrderRequest orderRequest) {
        try {
            List<OrderDetail> orderDetails = orderUtil.getSaveOrderDetails(orderRequest);
            Order order = orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException("order not found by id: "+ orderId));
            order.setTotal(orderRequest.getTotal());
            if (orderRequest.getDetails() == null){
                for (OrderDetail item : order.getDetails()){
                    orderDetailRepository.deleteById(item.getId());
                }
            }
            order.setDetails(orderDetails);
            order.setUpdatedOn(Time.NOW.toLocalDateTime());
            return orderUtil.mapOrderToOrderDto(orderRepository.save(order));
        } catch (ServerErrorException e) {
            throw new ServerErrorException("Đã xảy ra lỗi khi cập nhật order ",e);
        }
    }

    @Override
    public MessageApi deleteOrder(String orderId) {
        try {
            OrderDto orderDto = getOrderById(orderId);
            if (orderDto != null){
                List<OrderDetail> orderDetails = orderDto.getDetails();
                for (OrderDetail orderDetail : orderDetails){
                    orderDetailRepository.deleteById(orderDetail.getId());
                }
                orderRepository.deleteById(orderId);
                return MessageApi.builder().message("deleted successfully").timestamp(Time.NOW).build();
            }
            return null;
        } catch (ServerErrorException e) {
            throw new ServerErrorException("Đã xảy ra lỗi khi xóa 1 order ",e);
        }
    }


    @Override
    public MessageApi deleteAllOrder() {
        try {
            orderDetailRepository.deleteAll();
            orderRepository.deleteAll();
            return MessageApi.builder().message("deleted all successfully").timestamp(Time.NOW).build();
        } catch (ServerErrorException e) {
            throw new ServerErrorException("Đã xảy ra lỗi khi xóa tất cả order ",e);
        }
    }
}
