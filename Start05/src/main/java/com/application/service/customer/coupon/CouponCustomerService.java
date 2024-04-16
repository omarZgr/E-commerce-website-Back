package com.application.service.customer.coupon;

import com.application.entity.Coupon;

import java.util.Optional;

public interface CouponCustomerService {

    public Optional<Coupon> getCouponByCode(String  code) ;
    }
