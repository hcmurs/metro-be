package com.example.cronjob.Mapping;

import com.example.cronjob.DTO.Response.OrderResponse;
import com.example.cronjob.Pojos.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = TransactionMapping.class)
public interface OrderMapping {
    @Mapping(source = "transaction", target = "transaction")
    OrderResponse toResponse(Orders orders);

    Orders toEntity(OrderResponse orderResponse);

    @Mapping(source = "transaction", target = "transaction")
    List<OrderResponse> toResponseList(List<Orders> orders);

}
