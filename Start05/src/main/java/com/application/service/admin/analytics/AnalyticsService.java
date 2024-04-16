package com.application.service.admin.analytics;

import org.springframework.http.ResponseEntity;

public interface AnalyticsService {


    public ResponseEntity getTotal()  ;

    public ResponseEntity getTotalTotalAmount()  ;

    public ResponseEntity getCountOfRowsWithCoupon()  ;
}
