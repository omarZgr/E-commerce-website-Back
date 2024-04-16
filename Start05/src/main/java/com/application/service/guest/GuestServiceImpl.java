package com.application.service.guest;


import com.application.dto.OrderDto;
import com.application.entity.Order;
import com.application.mapper.order.OrderMapper;
import com.application.service.customer.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService{

    private final OrderService orderService ;
    private final OrderMapper orderMapper  ;

    public ResponseEntity trackOrder(String trackingId)
    {
        Optional<Order> optionalOrder = orderService.findByTrackingId(trackingId)  ;

        if (optionalOrder.isPresent())
        {

            OrderDto orderDto =orderMapper.mapper(optionalOrder.get())  ;

            return ResponseEntity.ok(orderDto)  ;
         }
        else
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This order not found !! ,trackingId ::"+trackingId)  ;
    }
}
