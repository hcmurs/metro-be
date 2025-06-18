package com.example.cronjob.Mapping;

import com.example.cronjob.DTO.Response.PaymentMethodResponse;
import com.example.cronjob.Pojos.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapping {
    @Mapping(source = "name", target = "paymentMethodName")
    PaymentMethodResponse toResponse(PaymentMethod paymentMethod);

    PaymentMethod toEntity(PaymentMethodResponse paymentMethodResponse);

    @Mapping(source = "name", target = "paymentMethodName")
    List<PaymentMethodResponse> toResponseList(List<PaymentMethod> paymentMethods);
}
