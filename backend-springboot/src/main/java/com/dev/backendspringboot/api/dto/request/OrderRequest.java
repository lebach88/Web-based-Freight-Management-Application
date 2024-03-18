package com.dev.backendspringboot.api.dto.request;
import com.dev.backendspringboot.document.OrderDetail;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<OrderDetail> details;
    private double total;
}
