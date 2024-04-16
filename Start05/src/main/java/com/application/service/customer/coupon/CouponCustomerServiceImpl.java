package com.application.service.customer.coupon;

import com.application.entity.Coupon;
import com.application.mapper.coupon.CouponMapper;
import com.application.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CouponCustomerServiceImpl implements CouponCustomerService{

    private final CouponRepository couponRepository  ;

    public Optional<Coupon> getCouponByCode(String  code){
        Optional<Coupon> optionalCoupon = couponRepository.findByCode(code);


            return optionalCoupon ;

    }

}
