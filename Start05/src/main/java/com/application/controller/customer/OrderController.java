package com.application.controller.customer;


import com.application.repository.CarteItemRepository;
import com.application.service.customer.carteItem.CarteItemService;
import com.application.service.customer.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Log4j2
public class OrderController {

    private final OrderService  orderService ;
    private final CarteItemService carteItemService ;

    @GetMapping("/order/init/{idUser}")
    public ResponseEntity<?> initOrder(@PathVariable long idUser)
    {
        try {

            return ResponseEntity.ok( orderService.initOrder(idUser))  ;

        }catch (Exception  exception)
        {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This user already have pending order");

        }
    }

    @GetMapping("/addProduct/{idUser}/{idProduct}")
    public ResponseEntity<?> addToPanier(@PathVariable long idUser,@PathVariable long idProduct)
    {
        return orderService.addProduct(idUser,idProduct)  ;
    }

    @GetMapping("/coupon/{code}/{orderId}")
    public ResponseEntity<?> applyCoupon(@PathVariable String code,@PathVariable long orderId)
    {

        if (code!=null)
        {
            return orderService.applyCoupon(code,orderId)  ;
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Code coupon must be not Empty")  ;
    }

    @GetMapping("/carteItemIn/{idCarteItem}")
    public ResponseEntity<?> incrementQuantity(@PathVariable long idCarteItem)
    {

        return orderService.incrementQuantityV2(idCarteItem) ;

    }

    @GetMapping("/carteItemDe/{idCarteItem}")
    public ResponseEntity<?> decrementQuantity(@PathVariable long idCarteItem)
    {

        return orderService.decrementQuantityV2(idCarteItem) ;

    }

    @GetMapping("/order/{idUser}/{idOrder}/{description}/{adress}")
    public ResponseEntity<?> placerOrder(@PathVariable long idUser,@PathVariable long idOrder,@PathVariable String description,@PathVariable String adress)
    {
        return orderService.placerOrder(idUser,idOrder,adress,description)  ;
    }

    @GetMapping("/order/placed/{idUser}")
    public ResponseEntity<?> getOrderPlaced(@PathVariable long idUser)
    {
        return orderService.getOrderPlaced(idUser)  ;
    }


    @GetMapping("/order/delivered/{idUser}")
    public ResponseEntity<?> getOrderDelivered(@PathVariable long idUser)
    {
        return orderService.getOrderDelivered(idUser)  ;
    }

    @GetMapping("/order/shipped/{idUser}")
    public ResponseEntity<?> getOrderShipped(@PathVariable long idUser)
    {
        return orderService.getOrderShipped(idUser)  ;
    }

    @GetMapping("/order/pending/{idUser}")
    public ResponseEntity<?> getCartOfOrderPending(@PathVariable long idUser)
    {
        return orderService.getOrderPending(idUser)  ;
    }

    @GetMapping("/card/{idCard}")
    public int getQuantityInCart(@PathVariable long idCard)
    {
        return carteItemService.getQuantityInCart(idCard)  ;
    }

    @DeleteMapping("/card/{idCard}")
    public ResponseEntity deleteCard(@PathVariable long idCard)
    {

        return  carteItemService.deleteFromCart(idCard)  ;
    }

    @GetMapping("/order/pendingOrder/{idUser}")
    public ResponseEntity<?> getOrderPendingForOrder(@PathVariable long idUser)
    {
        return orderService.getOrderPendingForOrder(idUser)  ;
    }


}
