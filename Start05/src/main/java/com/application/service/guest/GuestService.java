package com.application.service.guest;

import org.springframework.http.ResponseEntity;

public interface GuestService {

    public ResponseEntity trackOrder(String trackingId)  ;
}
