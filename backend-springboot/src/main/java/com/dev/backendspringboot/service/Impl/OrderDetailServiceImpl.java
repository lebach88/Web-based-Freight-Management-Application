package com.dev.backendspringboot.service.Impl;

import com.dev.backendspringboot.api.dto.request.OrderDetailRequest;
import com.dev.backendspringboot.api.dto.response.MessageApi;
import com.dev.backendspringboot.api.dto.response.OrderDetailDto;
import com.dev.backendspringboot.api.error.OrderDetailNotFoundException;
import com.dev.backendspringboot.api.error.ServerErrorException;
import com.dev.backendspringboot.document.Order;
import com.dev.backendspringboot.document.OrderDetail;
import com.dev.backendspringboot.repository.OrderDetailRepository;
import com.dev.backendspringboot.repository.OrderRepository;
import com.dev.backendspringboot.service.OrderDetailService;
import com.dev.backendspringboot.service.Time;
import com.dev.backendspringboot.service.util.OrderDetailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    private OrderDetailRepository orderDetailRepository;
    private OrderDetailUtil orderDetailUtil;
    private OrderRepository orderRepository;

    @Autowired
    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, OrderDetailUtil orderDetailUtil, OrderRepository orderRepository){
        this.orderDetailRepository = orderDetailRepository;
        this.orderDetailUtil = orderDetailUtil;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDetailDto> getAllOrderDetails() {
        try{
            List<OrderDetail> orderDetails = orderDetailRepository.findAll();
            return orderDetails.stream().map(item -> orderDetailUtil.mapOrderDetailToOrderDetailDto(item)).collect(Collectors.toList());
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi lấy danh sách order-detail ", ex);
        }
    }

    @Override
    public OrderDetailDto getOrderDetailById(String orderDetailId) {
        try{
            OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                    .orElseThrow(()-> new OrderDetailNotFoundException("order-detail not found by id:"+orderDetailId));
            return orderDetailUtil.mapOrderDetailToOrderDetailDto(orderDetail);
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi lấy 1 order-detail ", ex);
        }
    }

    @Override
    public OrderDetailDto createOrderDetail(OrderDetailRequest orderDetailRequest) {
        try{
            OrderDetail newOrderDetail = orderDetailRepository.save(orderDetailUtil.mapOrderDetailRequestToOrderDetail(orderDetailRequest));
            return orderDetailUtil.mapOrderDetailToOrderDetailDto(newOrderDetail);
        }catch (ServerErrorException ex){
            throw new ServerErrorException("Đã xảy ra lỗi khi tạo 1 order-detail ", ex);
        }
    }

    @Override
    public OrderDetailDto updateOrderDetail(String orderDetailId, OrderDetailRequest orderDetailRequest) {
        try {
            OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                    .orElseThrow(()-> new OrderDetailNotFoundException("order-detail not found by id:"+orderDetailId));
            orderDetail.setPrice(orderDetailRequest.getPrice());
            orderDetail.setQuantity(orderDetailRequest.getQuantity());
            OrderDetail updateOrderDetail = orderDetailRepository.save(orderDetail);
            return orderDetailUtil.mapOrderDetailToOrderDetailDto(updateOrderDetail);
        } catch (ServerErrorException e) {
            throw new ServerErrorException("Đã xảy ra lỗi khi cập nhật 1 order-detail ",e);
        }
    }

    @Override
    public MessageApi deleteOrderDetail(String orderDetailId) {
        try {
            OrderDetailDto orderDetailDto = getOrderDetailById(orderDetailId);
            if (orderDetailDto != null){
                List<Order> orders = orderRepository.findAll();
//  TODO:              thực hiện các tác vụ không đồng bộ
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(() -> {
                    for (Order order : orders){
                        if (order.getDetails().removeIf(detail -> Objects.equals(detail.getId(), orderDetailId))){
                            orderRepository.save(order);
                        }
                    }
                    orderDetailRepository.deleteById(orderDetailId);
                });
                executor.shutdown();
                return MessageApi.builder().message("delete successfully").timestamp(Time.NOW).build();
            }
            return null;
        } catch (ServerErrorException e) {
            throw new ServerErrorException("Đã xảy ra lỗi khi xóa 1 order-detail ",e);
        }
    }


    @Override
    public MessageApi deleteAllOrderDetail() {
        try {
            for (Order order : orderRepository.findAll()){
                order.setDetails(null);
                orderRepository.save(order);
            }
            orderDetailRepository.deleteAll();
            return MessageApi.builder().message("deleted all successfully").timestamp(Time.NOW).build();
        } catch (ServerErrorException e) {
            throw new ServerErrorException("Đã xảy ra lỗi khi xóa tất cả order-detail ",e);
        }
    }
}
