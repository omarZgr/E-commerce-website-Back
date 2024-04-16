package com.application.controller.admin;


import com.application.dto.CouponDto;
import com.application.service.admin.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Log4j2
public class CouponController {

    private final CouponService couponService ;


    @GetMapping("/coupons")
    public ResponseEntity<?> getAllProduct() {

        return couponService.getAllCoupons() ;

    }

    @GetMapping("/coupon/{couponCode}")
    public ResponseEntity<?> getProductByName(@PathVariable String  couponCode) {

        return couponService.getCouponByCode(couponCode) ;


    }

    @PostMapping("/coupon")
    public ResponseEntity<?> addProduct(@RequestBody CouponDto couponDto)
    {

        if (couponDto!=null && (couponDto.getName() !=null  || couponDto.getCode() !=null))
        {
            return couponService.addCoupon(couponDto) ;


        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("coupon was be not Null")  ;
    }



}
