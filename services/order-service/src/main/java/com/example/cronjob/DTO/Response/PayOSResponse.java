package com.example.cronjob.DTO.Response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PayOSResponse {
    private String code;
    private String id;
    private boolean cancel;
    private String status;
    private Long orderCode;
    private String signature; // Thêm trường signature để xác thực
}
