package com.application.service.customer.order;

import com.application.entity.Order;
import com.application.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    public Optional<Order> findById(long idOrder)  ;

    public ResponseEntity<?> addProduct(long idUser,long idProduct)   ;

    public ResponseEntity<?> getOrderPendingForOrder(long idUser)  ;

    public Order initOrder(long idUser)  ;



    public ResponseEntity<?> applyCoupon(String code,long orderId)  ;

    public ResponseEntity<?> incrementQuantityV2(long idCarteItem)  ;

    public ResponseEntity<?> decrementQuantityV2(long idCarteItem) ;

    public ResponseEntity<?>  placerOrder(long idUser,long idOrder,String adress,String desription)  ;

     Optional<Order> findByIdAndDelivered(long idOrder)  ;

    public ResponseEntity<?> getOrderPending(long idUser)  ;

    public ResponseEntity<?> getOrderPlaced(long idUser)  ;

    public ResponseEntity<?> getOrderShipped(long idUser)  ;

    public ResponseEntity<?> getOrderDelivered(long idUser)  ;


    Optional<Order> findByTrackingId(String trackingId);


    Order save(Order order);


}
