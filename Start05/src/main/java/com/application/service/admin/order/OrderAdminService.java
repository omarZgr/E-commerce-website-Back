package com.application.service.admin.order;

import org.springframework.http.ResponseEntity;

public interface OrderAdminService {

    public ResponseEntity<?> getOrderPending(long idUser)  ;

    public ResponseEntity<?> getOrderPlaced(long idUser)  ;

    public ResponseEntity<?> getOrderShipped(long idUser)  ;

    public ResponseEntity<?> getOrderDelivered(long idUser)  ;

    public ResponseEntity setOrderShipped(long idOrder)  ;

    public Double getTotalPayment()   ;

    public Double getTotalTotalAmount()  ;

    public Long getCountOfRowsWithCoupon()  ;

    public ResponseEntity setOrderDelivred(long idOrder)  ;

    public ResponseEntity getAllOrderPending() ;

    public ResponseEntity<?> getAllOrderShipped()  ;

    public ResponseEntity<?> getAllOrderDelivered()  ;

    public ResponseEntity<?> getAllOrderPlaced()  ;


    ResponseEntity<?> getOrderById(long idOrder);
}
