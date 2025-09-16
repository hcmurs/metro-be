/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Order-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.cronjob.Mapping;

import com.example.cronjob.DTO.Response.OrderResponse;
import com.example.cronjob.Pojos.Orders;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TransactionMapping.class)
public interface OrderMapping {
  @Mapping(source = "transaction", target = "transaction")
  OrderResponse toResponse(Orders orders);

  Orders toEntity(OrderResponse orderResponse);

  @Mapping(source = "transaction", target = "transaction")
  List<OrderResponse> toResponseList(List<Orders> orders);
}
