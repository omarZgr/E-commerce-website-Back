package com.application.mapper.order;


import com.application.dto.CarteItemDto;
import com.application.dto.OrderDto;
import com.application.entity.CarteItem;
import com.application.entity.Order;
import com.application.mapper.carteItem.CarteItemMapper;
import com.application.mapper.coupon.CouponMapper;
import com.application.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapperImpl implements OrderMapper{

    private  UserMapper userMapper ;
    private  CouponMapper couponMapper ;

    @Autowired
    @Lazy
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    @Lazy
    public void setCouponMapper(CouponMapper couponMapper) {
        this.couponMapper = couponMapper;
    }


    public OrderDto mapper(Order order)
    {
        OrderDto orderDto  = new OrderDto() ;

        orderDto.setId(order.getId());
        orderDto.setDiscount(order.getDiscount());
        orderDto.setAdress(order.getAdress());
        orderDto.setOrderStatus(order.getOrderStatus());
        orderDto.setPayment(order.getPayment());
        orderDto.setTotalAmount(order.getTotalAmount());
        orderDto.setTrackingId(order.getTrackingId());
        orderDto.setOrderDescription(order.getOrderDescription());
        orderDto.setDateCreation(order.getDateCreation());

        if (order.getUser()!=null)
             orderDto.setUserDto(userMapper.mapper(order.getUser()));

        if (order.getCoupon()!=null)
            orderDto.setCouponDto(couponMapper.mapper(order.getCoupon()));




        return orderDto ;
    }

    public Set<OrderDto> mapper(Set<Order>orders)
    {
        Set<OrderDto> orderDtos = new HashSet<>();

        for (Order order:orders)
            orderDtos.add(mapper(order)) ;

        return orderDtos  ;
    }

    public Order unmapper(OrderDto orderDto) throws IOException {
        Order order  = new Order() ;

        order.setId(orderDto.getId());
        order.setDiscount(orderDto.getDiscount());
        order.setAdress(orderDto.getAdress());
        order.setOrderStatus(orderDto.getOrderStatus());
        order.setPayment(orderDto.getPayment());
        order.setTotalAmount(orderDto.getTotalAmount());
        order.setTrackingId(orderDto.getTrackingId());
        order.setOrderDescription(orderDto.getOrderDescription());
        order.setDateCreation(orderDto.getDateCreation());

        if (orderDto.getUserDto()!=null)
           order.setUser(userMapper.unmapper(orderDto.getUserDto()));

        if (orderDto.getCouponDto()!=null)
            order.setCoupon(couponMapper.unmapper(orderDto.getCouponDto()));


        return order ;
    }

    public Set<Order> unmapper(Set<OrderDto>orderDtos) throws IOException {
        Set<Order> orders = new HashSet<>();

        for (OrderDto orderDto:orderDtos)
            orders.add(unmapper(orderDto)) ;

        return orders  ;
    }
}
