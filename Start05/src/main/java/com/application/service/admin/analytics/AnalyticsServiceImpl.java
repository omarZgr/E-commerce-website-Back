package com.application.service.admin.analytics;

import com.application.service.admin.order.OrderAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AnalyticsServiceImpl implements AnalyticsService{

    private final OrderAdminService orderAdminService  ;

    public ResponseEntity getTotal()
    {

        return ResponseEntity.ok(orderAdminService.getTotalPayment())  ;

    }

    public ResponseEntity getTotalTotalAmount()
    {

        return ResponseEntity.ok(orderAdminService.getTotalTotalAmount())  ;

    }

    public ResponseEntity getCountOfRowsWithCoupon()
    {

        return ResponseEntity.ok(orderAdminService.getCountOfRowsWithCoupon())  ;

    }


}
