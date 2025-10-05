package com.example.cronjob.DTO.Request;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PayOSWebhookRequest {
    private String code;
    private String desc;
    private WebhookData data;
    private String signature;

    @Data
    @NoArgsConstructor
    public static class WebhookData {
        @JsonProperty("orderCode")
        private Long orderCode;
        private int amount;
        private String description;
        private String status; // Trạng thái quan trọng: PAID, CANCELLED, EXPIRED
    }
}