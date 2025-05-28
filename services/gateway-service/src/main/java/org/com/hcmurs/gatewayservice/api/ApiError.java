package org.com.hcmurs.gatewayservice.api;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"message", "reason", "status_code", "is_success", "data"})
public record ApiError<T>(
    Integer statusCode,
    String message,
    String reason,
    Boolean isSuccess,
    T data
) {

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static final class Builder<T> {
        private Integer statusCode;
        private String message;
        private String reason;
        private Boolean isSuccess;
        private T data;

        public Builder<T> statusCode(Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder<T> isSuccess(Boolean isSuccess) {
            this.isSuccess = isSuccess;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ApiError<T> build() {
            return new ApiError<>(statusCode, message, reason, isSuccess, data);
        }
    }
}

