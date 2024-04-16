package com.application.controller.admin;

import com.application.service.admin.order.OrderAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Log4j2
public class OrderAdminController {

    private final OrderAdminService orderAdminService ;


    @GetMapping("/order/pending/{idUser}")
    public ResponseEntity<?> getOrderPending(@PathVariable long idUser)
    {
        return orderAdminService.getOrderPending(idUser)  ;
    }

    @GetMapping("/order/placed/{idUser}")
    public ResponseEntity<?> getOrderPlaced(@PathVariable long idUser)
    {
        return orderAdminService.getOrderPlaced(idUser)  ;
    }


    @GetMapping("/order/shipped/{idUser}")
    public ResponseEntity<?> getOrderShipped(@PathVariable long idUser)
    {
        return orderAdminService.getOrderShipped(idUser)  ;
    }

    @GetMapping("/order/delivered/{idUser}")
    public ResponseEntity<?> getOrderDelivered(@PathVariable long idUser)
    {
        return orderAdminService.getOrderDelivered(idUser)  ;
    }


    @GetMapping("/order/setShipped/{idOrder}")
    public ResponseEntity<?> setOrderShipped(@PathVariable long idOrder)
    {
        return orderAdminService.setOrderShipped(idOrder) ;
    }

    @GetMapping("/order/setDelivered/{idOrder}")
    public ResponseEntity<?> setOrderDelivred(@PathVariable long idOrder)
    {
        return orderAdminService.setOrderDelivred(idOrder) ;
    }


    @GetMapping("/order/pending")
    public ResponseEntity<?> getAllOrderPending()
    {
        return orderAdminService.getAllOrderPending()  ;
    }


    @GetMapping("/order/shipped")
    public ResponseEntity<?> getAllOrderShipped()
    {
        return orderAdminService.getAllOrderShipped()  ;
    }


    @GetMapping("/order/placed")
    public ResponseEntity<?> getAllOrderPlaced()
    {
        return orderAdminService.getAllOrderPlaced()  ;
    }


    @GetMapping("/order/delivered")
    public ResponseEntity<?> getOrderDelivered()
    {
        return orderAdminService.getAllOrderDelivered()  ;
    }

    @GetMapping("/order/{idOrder}")
    public ResponseEntity<?> getOrderById(@PathVariable long idOrder)
    {
        return orderAdminService.getOrderById(idOrder)  ;
    }
}
