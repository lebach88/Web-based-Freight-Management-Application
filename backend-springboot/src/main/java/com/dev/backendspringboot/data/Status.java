package com.dev.backendspringboot.data;

public enum Status {
    //    Đang chờ gửi
    PENDING,
    //    Đang vận chuyển
    IN_PROGRESS,
    //    Đã giao
    COMPLETED,
    //    Đã hủy
    CANCELLED,
    //    Đang chờ nhận
    AWAITING_PICKUP,
    //    Đã trả lại
    RETURNED,
    //    Đang chờ xử lý
    PROCESSING,
    //Lưu trữ
    ARCHIVE
}
