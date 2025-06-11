package com.example.cronjob.Mapping;

import com.example.cronjob.DTO.Response.TransactionResponse;
import com.example.cronjob.Pojos.Transactions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapping {
    @Mapping(source = "paymentMethod.paymentMethodId", target = "paymentMethodId")
    @Mapping(source = "paymentMethod.name", target = "paymentMethodName")
    TransactionResponse toResponse(Transactions transaction);

    Transactions toEntity(TransactionResponse transactionResponse);
    List<TransactionResponse> toResponseList(List<Transactions> transactions);

}
