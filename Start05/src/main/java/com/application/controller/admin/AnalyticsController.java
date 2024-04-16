package com.application.controller.admin;

import com.application.service.admin.analytics.AnalyticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Log4j2
public class AnalyticsController {

    private final AnalyticsService analyticsService  ;


    @GetMapping("/analytics/gain")
    public ResponseEntity getPayment()
    {

        return analyticsService.getTotal()  ;
    }

    @GetMapping("/analytics/totalAmount")
    public ResponseEntity getTotalTotalAmount()
    {

        return analyticsService.getTotalTotalAmount()  ;
    }

    @GetMapping("/analytics/couponUse")
    public ResponseEntity getCountOfRowsWithCoupon()
    {

        return analyticsService.getCountOfRowsWithCoupon()  ;
    }

}
