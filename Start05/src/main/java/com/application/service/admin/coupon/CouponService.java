package com.application.service.admin.coupon;

import com.application.dto.CouponDto;
import org.springframework.http.ResponseEntity;

public interface CouponService {

    public ResponseEntity<?> getAllCoupons()  ;

    public ResponseEntity<?> getCouponById(long id)  ;

    public ResponseEntity<?> getCouponByCode(String  code) ;

    public ResponseEntity<?> addCoupon(CouponDto couponDto)  ;
}
