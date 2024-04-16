package com.application.mapper.coupon;

import com.application.dto.CouponDto;
import com.application.dto.OrderDto;
import com.application.entity.Coupon;
import com.application.entity.Order;
import com.application.mapper.order.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class CouponMapperImpl implements CouponMapper{



    public CouponDto mapper(Coupon coupon)
    {
        CouponDto couponDto = new CouponDto()  ;

        couponDto.setId(coupon.getId());
        couponDto.setName(coupon.getName());
        couponDto.setCode(coupon.getCode());
        couponDto.setDiscount(coupon.getDiscount());
        couponDto.setExpirationDate(coupon.getExpirationDate());




        return couponDto ;
    }

    public List<CouponDto> mapper(List<Coupon> coupons)
    {
        List<CouponDto> couponDtos = new ArrayList<>();

        for (Coupon coupon:coupons)
        {
            couponDtos.add(mapper(coupon))  ;
        }

        return couponDtos  ;
    }

    public Coupon unmapper(CouponDto couponDto)
    {

        Coupon coupon  = new Coupon()  ;


        coupon.setId(couponDto.getId());
        coupon.setName(couponDto.getName());
        coupon.setCode(couponDto.getCode());
        coupon.setDiscount(couponDto.getDiscount());
        coupon.setExpirationDate(couponDto.getExpirationDate());





        return coupon ;


    }

    public List<Coupon> unmapper(List<CouponDto> couponDtos)
    {
        List<Coupon> coupons = new ArrayList<>() ;

        for (CouponDto couponDto:couponDtos)
        {
            coupons.add(unmapper(couponDto))  ;
        }

        return coupons  ;

    }
}
