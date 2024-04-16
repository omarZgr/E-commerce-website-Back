package com.application.mapper.coupon;

import com.application.dto.CouponDto;
import com.application.entity.Coupon;

import java.util.List;

public interface CouponMapper {

    public CouponDto mapper(Coupon coupon)  ;

    public List<CouponDto> mapper(List<Coupon> coupons)  ;

    public Coupon unmapper(CouponDto couponDto)  ;

    public List<Coupon> unmapper(List<CouponDto> couponDtos)  ;
}
