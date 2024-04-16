package com.application.mapper.order;

import com.application.dto.OrderDto;
import com.application.entity.Order;

import java.io.IOException;
import java.util.Set;

public interface OrderMapper {

    public OrderDto mapper(Order order)  ;

    public Set<OrderDto> mapper(Set<Order>orders)  ;

    public Order unmapper(OrderDto orderDto) throws IOException;

    public Set<Order> unmapper(Set<OrderDto>orderDtos) throws IOException;
}
