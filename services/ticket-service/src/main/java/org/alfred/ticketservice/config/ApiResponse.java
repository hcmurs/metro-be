/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
  int status;
  String message;
  T data;

  public static <T> ApiResponse<T> success(T data, String message) {
    return ApiResponse.<T>builder().status(200).data(data).message(message).build();
  }

  public static <T> ApiResponse<T> success(T data) {
    return success(data, "Success");
  }

  public static <T> ApiResponse<T> success(String message) {
    return success(null, message);
  }

  public static ApiResponse<Void> error(int status, @NonNull String message) {
    return ApiResponse.<Void>builder().status(status).message(message).build();
  }
}
