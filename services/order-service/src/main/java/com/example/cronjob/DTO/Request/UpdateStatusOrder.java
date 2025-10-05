package com.example.cronjob.DTO.Request;

import com.example.cronjob.Enum.OrderStatus;
import lombok.Data;

@Data
public class UpdateStatusOrder {
    private Long orderCode; // mã đơn hàng
    private  OrderStatus status;    // "SUCCESS", "CANCELLED"
}
