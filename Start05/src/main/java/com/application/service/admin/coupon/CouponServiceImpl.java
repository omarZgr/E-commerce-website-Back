package com.application.service.admin.coupon;


import com.application.dto.CouponDto;
import com.application.entity.Coupon;
import com.application.exception.HandleException;
import com.application.mapper.coupon.CouponMapper;
import com.application.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Log4j2
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService{

    private final CouponRepository couponRepository  ;
    private final CouponMapper couponMapper ;

    public ResponseEntity<?> getAllCoupons()
    {
        List<Coupon> coupons = couponRepository.findAllByOrderByExpirationDateDesc()  ;

        if (coupons.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.OK).body("Coupons Empty");

        }
        else
        {
            List<CouponDto> couponDtos = couponMapper.mapper(coupons)  ;
            return ResponseEntity.status(HttpStatus.OK).body(couponDtos);

        }    }

    public ResponseEntity<?> getCouponById(long id)
    {
        Optional<Coupon> optionalCoupon = couponRepository.findById(id);

        if (optionalCoupon.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(couponMapper.mapper(optionalCoupon.get()))  ;
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new HandleException("coupon not found for id: " + id)) ;
        }
    }

    public ResponseEntity<?> getCouponByCode(String  code
    ) {
        Optional<Coupon> optionalCoupon = couponRepository.findByCode(code);

        if (optionalCoupon.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(couponMapper.mapper(optionalCoupon.get()))  ;
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new HandleException("coupon not found for code: " + code)) ;
        }
    }

    public ResponseEntity<?> addCoupon(CouponDto couponDto)
    {

        log.warn("couponDto >>>>>> "+couponDto);


        Coupon coupon =  couponMapper.unmapper(couponDto)  ;





            log.warn("coupon >>>>>> "+coupon);


            Optional<Coupon> optionalCoupon = couponRepository.findByName(coupon.getName())  ;



            if (!optionalCoupon.isPresent())
                return ResponseEntity.status(HttpStatus.CREATED).body(couponMapper.mapper(couponRepository.save(coupon))) ;

            else

                return ResponseEntity.status(HttpStatus.CONFLICT).body(new HandleException("This name of coupon already exist!"))  ;

        }



    }





