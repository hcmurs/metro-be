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

import com.example.cronjob.DTO.Response.PaymentMethodResponse;
import com.example.cronjob.Pojos.PaymentMethod;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapping {
  @Mapping(source = "name", target = "paymentMethodName")
  PaymentMethodResponse toResponse(PaymentMethod paymentMethod);

  PaymentMethod toEntity(PaymentMethodResponse paymentMethodResponse);

  @Mapping(source = "name", target = "paymentMethodName")
  List<PaymentMethodResponse> toResponseList(List<PaymentMethod> paymentMethods);
}
