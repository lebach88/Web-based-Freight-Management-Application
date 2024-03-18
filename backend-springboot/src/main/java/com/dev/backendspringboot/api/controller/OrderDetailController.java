package com.dev.backendspringboot.api.controller;

import com.dev.backendspringboot.api.dto.request.OrderDetailRequest;
import com.dev.backendspringboot.api.dto.response.MessageApi;
import com.dev.backendspringboot.api.dto.response.OrderDetailDto;
import com.dev.backendspringboot.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-detail/")
public class OrderDetailController {
    private OrderDetailService orderDetailService;
    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderDetailDto>> getAllOrderDetails() {
        return ResponseEntity.ok(orderDetailService.getAllOrderDetails());
    }

    @GetMapping("/{orderDetailId}")
    public ResponseEntity<OrderDetailDto> getOrderDetailById(@PathVariable String orderDetailId) {
        return ResponseEntity.ok(orderDetailService.getOrderDetailById(orderDetailId));
    }

    @PostMapping("/")
    public ResponseEntity<OrderDetailDto> createOrderDetail(@RequestBody OrderDetailRequest orderDetailRequest) {
        return ResponseEntity.ok(orderDetailService.createOrderDetail(orderDetailRequest));
    }

    @PutMapping("/{orderDetailId}")
    public ResponseEntity<OrderDetailDto> updateOrderDetail(@PathVariable String orderDetailId, @RequestBody OrderDetailRequest orderDetailRequest) {
        return ResponseEntity.ok(orderDetailService.updateOrderDetail(orderDetailId, orderDetailRequest));
    }

    @DeleteMapping("/{orderDetailId}")
    public ResponseEntity<MessageApi> deleteOrderDetail(@PathVariable String orderDetailId) {
        return ResponseEntity.ok(orderDetailService.deleteOrderDetail(orderDetailId));
    }
    @DeleteMapping("/")
    public ResponseEntity<MessageApi> deleteAllOrderDetail(){
        return ResponseEntity.ok(orderDetailService.deleteAllOrderDetail());
    }
}
