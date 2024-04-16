package com.application.service.admin.order;


import com.application.dto.CarteItemDto;
import com.application.dto.OrderDto;
import com.application.entity.CarteItem;
import com.application.entity.Order;
import com.application.enums.OrderStatus;
import com.application.mapper.carteItem.CarteItemMapper;
import com.application.mapper.order.OrderMapper;
import com.application.repository.OrderRepository;
import com.application.service.admin.carteItem.CarteItemAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderAdminServiceImpl implements OrderAdminService{

    private final OrderRepository  orderRepository ;
            ;
    private final CarteItemAdminService carteItemAdminService  ;


    private final OrderMapper orderMapper ;

    private final CarteItemMapper carteItemMapper;


    public ResponseEntity<?> getOrderPending(long idUser)
    {
        return ResponseEntity.ok(orderMapper.mapper(new HashSet<>( orderRepository.findAllByUserIdAndOrderStatus(idUser,OrderStatus.Pending))))  ;

    }

    public ResponseEntity<?> getOrderPlaced(long idUser)
    {
        return ResponseEntity.ok(orderMapper.mapper(new HashSet<>( orderRepository.findAllByUserIdAndOrderStatus(idUser,OrderStatus.Placed))))  ;

    }

    public ResponseEntity<?> getOrderShipped(long idUser)
    {
        return ResponseEntity.ok(orderMapper.mapper(new HashSet<>( orderRepository.findAllByUserIdAndOrderStatus(idUser,OrderStatus.Shipped))))  ;

    }

    public ResponseEntity<?> getOrderDelivered(long idUser)
    {
        return ResponseEntity.ok(orderMapper.mapper(new HashSet<>( orderRepository.findAllByUserIdAndOrderStatus(idUser,OrderStatus.Delivered))))  ;

    }


    public ResponseEntity setOrderShipped(long idOrder)
    {
        Optional<Order> optionalOrder = orderRepository.findById(idOrder) ;

        if (optionalOrder.isPresent())
        {

            log.warn("optionalOrder.get().getOrderStatus() >>> "+optionalOrder.get().getOrderStatus());
            log.warn("optionalOrder.get().getOrderStatus() >>> "+optionalOrder.get().getOrderStatus());
            boolean isPlaced = optionalOrder.get().getOrderStatus().equals(OrderStatus.Placed) ;

            if (isPlaced)
            {
                optionalOrder.get().setOrderStatus(OrderStatus.Shipped);
                ////send email ;

                String randomValue = getRandomValue();

                log.warn("randomValue >>>> "+randomValue);

                optionalOrder.get().setTrackingId(randomValue);


                OrderDto orderDto = orderMapper.mapper(orderRepository.save(optionalOrder.get())) ;

                return ResponseEntity.ok(orderDto)  ;
            }
            else
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This order not Placed  -- idOrder : "+idOrder)  ;


        }



            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This order not found -- idOrder : "+idOrder)  ;
    }

    public ResponseEntity setOrderDelivred(long idOrder)
    {
        Optional<Order> optionalOrder = orderRepository.findById(idOrder) ;

        if (optionalOrder.isPresent())
        {

            boolean isShipped = optionalOrder.get().getOrderStatus().equals(OrderStatus.Shipped) ;

            if (isShipped)
            {
                optionalOrder.get().setOrderStatus(OrderStatus.Delivered);
                ////send email ;


                OrderDto orderDto = orderMapper.mapper(orderRepository.save(optionalOrder.get())) ;

                return ResponseEntity.ok(orderDto)  ;
            }
            else
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This order not Shipped  -- idOrder : "+idOrder)  ;


        }



        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This order not found -- idOrder : "+idOrder)  ;
    }


    private  String getRandomValue() {
        int length = 12  ;
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid.substring(0, Math.min(length, uuid.length()));
    }


    public Double getTotalPayment()
    {
        return orderRepository.getTotalPayment();
    }

    public Double getTotalTotalAmount()
    {
        return orderRepository.getTotalTotalAmount();
    }

    public Long getCountOfRowsWithCoupon()
    {
        return orderRepository.getCountOfRowsWithCoupon();
    }


    public ResponseEntity<?> getAllOrderPending()
    {

        Set<OrderDto>orderDtos = orderMapper.mapper(new HashSet<>(orderRepository.findAllByOrderStatus(OrderStatus.Pending)))  ;
        return  ResponseEntity.ok(orderDtos)  ;        }

    public ResponseEntity<?> getAllOrderShipped()
    {

        Set<OrderDto>orderDtos = orderMapper.mapper(new HashSet<>(orderRepository.findAllByOrderStatus(OrderStatus.Shipped)))  ;
        return  ResponseEntity.ok(orderDtos)  ;
    }

    public ResponseEntity<?> getAllOrderDelivered()
    {

        Set<OrderDto>orderDtos = orderMapper.mapper(new HashSet<>(orderRepository.findAllByOrderStatus(OrderStatus.Delivered)))  ;
        return  ResponseEntity.ok(orderDtos)  ;        }

    public ResponseEntity<?> getAllOrderPlaced()
    {

        Set<OrderDto>orderDtos = orderMapper.mapper(new HashSet<>(orderRepository.findAllByOrderStatus(OrderStatus.Placed)))  ;
        return  ResponseEntity.ok(orderDtos)  ;    }

    @Override
    public ResponseEntity<?> getOrderById(long idOrder) {

        Optional<Order> order = orderRepository.findById(idOrder)  ;

        if (order.isPresent())
        {
            return ResponseEntity.ok(orderMapper.mapper(order.get())) ;
        }
        else
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Order not found") ;
    }

}
