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

import com.example.cronjob.DTO.Response.TransactionResponse;
import com.example.cronjob.Pojos.Transactions;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapping {
  @Mapping(source = "paymentMethod.paymentMethodId", target = "paymentMethodId")
  @Mapping(source = "paymentMethod.name", target = "paymentMethodName")
  TransactionResponse toResponse(Transactions transaction);

  Transactions toEntity(TransactionResponse transactionResponse);

  List<TransactionResponse> toResponseList(List<Transactions> transactions);
}
