package com.application.dto;


import com.application.entity.Order;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponDto {

    private  long id ;

    private String name ;

    private String code ;
    private  long discount ;
    private Date expirationDate ;




}
