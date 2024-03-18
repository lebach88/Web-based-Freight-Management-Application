package com.dev.backendspringboot.api.dto.response;

import com.dev.backendspringboot.document.OrderDetail;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderDto {
    private String id;
    private List<OrderDetail> details;
    private double total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedOn;
}
