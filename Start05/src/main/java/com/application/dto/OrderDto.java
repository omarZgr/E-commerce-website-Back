package com.application.dto;


import com.application.enums.OrderStatus;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private long id ;
    private String adress ;
    private String orderDescription ;
    private float discount ;
    private float payment ;
    private float totalAmount ;
    private String trackingId ;
    private Date dateCreation ;


    private OrderStatus orderStatus ;


    private UserDto userDto ;

    private CouponDto couponDto ;








}
