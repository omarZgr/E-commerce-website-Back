package com.application.service.customer.order;


import com.application.dto.CarteItemDto;
import com.application.dto.OrderDto;
import com.application.entity.*;
import com.application.enums.OrderStatus;
import com.application.exception.HandleException;
import com.application.mapper.carteItem.CarteItemMapper;
import com.application.mapper.order.OrderMapper;
import com.application.repository.OrderRepository;
import com.application.service.customer.carteItem.CarteItemService;
import com.application.service.customer.coupon.CouponCustomerService;
import com.application.service.customer.product.ProductServiceCustomer;
import com.application.service.customer.user.UserServiceCustomer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService{

    private final OrderRepository  orderRepository ;
    private  UserServiceCustomer userServiceCustomer  ;
    private final ProductServiceCustomer productServiceCustomer  ;
    private  CarteItemService carteItemService  ;

    private final CouponCustomerService  couponCustomerService  ;

    private final OrderMapper orderMapper ;

    private final CarteItemMapper carteItemMapper;

    @Autowired
    @Lazy
    public void setCarteItemService(CarteItemService carteItemService) {
        this.carteItemService = carteItemService;
    }
    ;


    @Autowired
    @Lazy
    public void setUserServiceCustomer(UserServiceCustomer userServiceCustomer) {
        this.userServiceCustomer = userServiceCustomer;
    }

    public ResponseEntity<?> addProduct(long idUser,long idProduct)
    {
        Optional<User> optionalUser = userServiceCustomer.findUserById(idUser) ;

        if (optionalUser.isPresent())
        {
            Optional<Product> optionalProduct = productServiceCustomer.findById(idProduct) ;

            if (optionalProduct.isPresent())
            {
                Order order = orderRepository.findByOrderStatusAndUserId(OrderStatus.Pending,idUser).get() ;

                if (order!=null)
                {
                    long idOrder = order.getId();
                    log.warn("This idOrder >> "+idOrder);
                    log.warn("(old)  -- Totaml amount : "+order.getTotalAmount());
                    log.warn("(old)  --  payment : "+order.getPayment());;
                    log.warn("(old)  -- descount : "+order.getDiscount());

                    List<CarteItem> carteItemList=carteItemService.findByOrderId(idOrder) ;

                    if (carteItemList!=null)
                    {
                        for (CarteItem carteItem:carteItemList)
                            if (carteItem.getProduct().getId()==idProduct)
                                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("This product already selcted !!")   ;

                        CarteItem carteItem = new CarteItem()  ;

                        carteItem.setOrder(order);
                        carteItem.setProduct(optionalProduct.get());
                        carteItem.setQuantity(1);
                        carteItem.setPrice(optionalProduct.get().getPrice());

                        carteItemService.save(carteItem)   ;


                        float totalPriceOfCartItem = carteItemService.getSumPriceOfCarteItem(order.getId())  ;

                        float newTotalAmount = totalPriceOfCartItem ;

                        float newPAyment = newTotalAmount ;

                        float newDiscount = 0f ;

                        log.warn("(intemeidaire)  -- Totaml amount : "+order.getTotalAmount());
                        log.warn("(intemeidaire)  --  payment : "+order.getPayment());;
                        log.warn("(intemeidaire)  -- descount : "+order.getDiscount());



                        if (order.getCoupon()!=null)
                            log.warn("AM HERE AND THIS IS VALUE OF COUPON >>>>> "+order.getCoupon().getId());

                        else
                            log.warn("AM HERE AND THIS IS VALUE OF COUPON >>>>>  NULL");
                        if (order.getCoupon()!=null)
                        {
                            long discount = order.getCoupon().getDiscount()  ;

                            newDiscount = (float) discount / 100.0f * newTotalAmount ;
                            newPAyment = totalPriceOfCartItem - newDiscount ;

                            log.warn("RANI DKHLT U VALUE OF newDiscount >>>  "+newDiscount);

                        }

                        order.setPayment(newPAyment);
                        order.setTotalAmount(newTotalAmount);
                        order.setDiscount(newDiscount);


                        Order orderUpated = orderRepository.save(order) ;

                        carteItem.setOrder(orderUpated);

                        log.warn("AM HERE");

                        return ResponseEntity.status(HttpStatus.CREATED).body(carteItemMapper.mapper(carteItem))  ;
                    }
                    else
                    {
                        ///sff dkhl produit
                        CarteItem carteItem = new CarteItem()  ;

                        carteItem.setOrder(order);
                        carteItem.setProduct(optionalProduct.get());
                        carteItem.setQuantity(1);
                        carteItem.setPrice(optionalProduct.get().getPrice());

                        CarteItemDto carteItemDto = carteItemMapper.mapper(carteItemService.save(carteItem))  ;

                        return ResponseEntity.status(HttpStatus.CREATED).body(carteItemDto)  ;
                    }
                }
                else
                {
                    try {
                        initOrder(idUser) ;
                    }catch (Exception exception)
                    {
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This user already have pending order");
                    }
                    return null ;

                }
            }

            else
                return ResponseEntity.ok("Product not found for id : "+idProduct)  ;


        }
        else
            return ResponseEntity.ok("User not found for id : "+idUser)  ;

    }




    public Order initOrder(long idUser)
    {

        Optional<User> user = userServiceCustomer.findUserById(idUser) ;

        if (orderRepository.findByUserIdAndOrderStatus(user.get().getId(),OrderStatus.Pending).isEmpty())
        {

            Order order = new Order()  ;

            order.setAdress("empty");
            order.setOrderDescription("empty");
            order.setDiscount(0);
            order.setPayment(0);
            order.setTotalAmount(0);
            order.setTrackingId(null);
            order.setDateCreation(new Date());
            order.setOrderStatus(OrderStatus.Pending);

            order.setCarteItems(null);

            order.setUser(user.get());


            orderRepository.save(order)  ;

            return order ;
        }
        else
        {
            throw  new  HandleException("This user already have pending order") ;
        }




    }



    public ResponseEntity<?> applyCoupon(String code,long orderId)
    {

            Optional<Coupon> optionalCoupon =couponCustomerService.getCouponByCode(code)  ;

            if (optionalCoupon.isPresent())
            {
                Optional<Order> activeOrder = orderRepository.findById(orderId)  ;

                if (activeOrder.isPresent())
                {

                    if (activeOrder.get().getCoupon()==null)
                    {

                        long discountCoupoun = optionalCoupon.get().getDiscount()  ;

                        float discountOrder = optionalCoupon.get().getDiscount()  ;

                        float oldPAyment = activeOrder.get().getPayment()  ;


                        discountOrder = (float) discountCoupoun / 100.f  * oldPAyment ;
                        float newPayment = oldPAyment - (float) discountCoupoun / 100.f  * oldPAyment ;

                        activeOrder.get().setDiscount(discountOrder);
                        activeOrder.get().setPayment(newPayment);
                        activeOrder.get().setCoupon(optionalCoupon.get());

                       Order orderUptated = orderRepository.save(activeOrder.get())  ;



                        return ResponseEntity.status(HttpStatus.OK).body(orderMapper.mapper(orderUptated))  ;
                    }
                    else

                    {
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("this order already apply coupon !!")  ;

                    }

                   }
                else
                {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("this order not exist or something went error !!")  ;
                }

            }
            else
            {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This coupon not exist")  ;
            }



    }

    public ResponseEntity<?> incrementQuantityV2(long idCarteItem)
    {
        Optional<CarteItem> carteItemSelected = carteItemService.findById(idCarteItem) ;

        if (carteItemSelected.isPresent())
        {
            Optional<Order> orderSelcted = orderRepository.findById(carteItemSelected.get().getOrder().getId()) ;
            log.warn("cart seletcted id ?>> "+ carteItemSelected.get().getId());

            if (orderSelcted.isPresent())
            {

                int oldQuantity = carteItemSelected.get().getQuantity() ;
                int newQuantity = oldQuantity + 1  ;

                float oldPrice = carteItemSelected.get().getPrice() ;
                float newPrice = carteItemSelected.get().getPrice() + carteItemSelected.get().getProduct().getPrice()  ;

                carteItemSelected.get().setQuantity(newQuantity);
                carteItemSelected.get().setPrice(newPrice);

                CarteItem carteItemUpdated = carteItemService.save(carteItemSelected.get());

                log.warn("Carte item increment Successfully !!");

                float oldTotalAmount = orderSelcted.get().getTotalAmount() ;

                float totalPriceOfCartItem = carteItemService.getSumPriceOfCarteItem(orderSelcted.get().getId())  ;

                float newTotalAmount = totalPriceOfCartItem ;

                float newPAyment = newTotalAmount ;

                float newDiscount = 0f ;


                if (orderSelcted.get().getCoupon()!=null)
                    log.warn("AM HERE AND THIS IS VALUE OF COUPON >>>>> "+orderSelcted.get().getCoupon().getId());

                else
                    log.warn("AM HERE AND THIS IS VALUE OF COUPON >>>>>  NULL");
                if (orderSelcted.get().getCoupon()!=null)
                {
                    long discount = orderSelcted.get().getCoupon().getDiscount()  ;

                    newDiscount = (float) discount / 100.0f * newTotalAmount ;
                    newPAyment = totalPriceOfCartItem - newDiscount ;

                    log.warn("RANI DKHLT U VALUE OF newDiscount >>>  "+newDiscount);

                }

                orderSelcted.get().setPayment(newPAyment);
                orderSelcted.get().setTotalAmount(newTotalAmount);
                orderSelcted.get().setDiscount(newDiscount);

                Order orderUpated = orderRepository.save(orderSelcted.get()) ;

                carteItemUpdated.setOrder(orderUpated);


                return ResponseEntity.status(HttpStatus.OK).body(carteItemMapper.mapper(carteItemUpdated))   ;








            }
            else
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This carteItem don't have Order (Order not exist)")   ;
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This carteItem not exist !!") ;
        }
    }


    public ResponseEntity<?> decrementQuantityV2(long idCarteItem)
    {
        Optional<CarteItem> carteItemSelected = carteItemService.findById(idCarteItem) ;

        if (carteItemSelected.isPresent())
        {
            Optional<Order> orderSelcted = orderRepository.findById(carteItemSelected.get().getOrder().getId()) ;

            if (orderSelcted.isPresent())
            {

                int oldQuantity = carteItemSelected.get().getQuantity() ;
                int newQuantity = oldQuantity - 1  ;

                float oldPrice = carteItemSelected.get().getPrice() ;
                float newPrice = carteItemSelected.get().getPrice() - carteItemSelected.get().getProduct().getPrice()  ;

                carteItemSelected.get().setQuantity(newQuantity);
                carteItemSelected.get().setPrice(newPrice);

                CarteItem carteItemUpdated = carteItemService.save(carteItemSelected.get());

                log.warn("Carte item increment Successfully !!");

                float oldTotalAmount = orderSelcted.get().getTotalAmount() ;

                float totalPriceOfCartItem = carteItemService.getSumPriceOfCarteItem(orderSelcted.get().getId())  ;

                float newTotalAmount = totalPriceOfCartItem ;

                float newPAyment = newTotalAmount ;

                float newDiscount = 0f ;

                if (orderSelcted.get().getCoupon()!=null)
                {
                    long discount = orderSelcted.get().getCoupon().getDiscount()  ;

                    newDiscount = (float) discount / 100.0f * newTotalAmount ;
                    newPAyment = totalPriceOfCartItem - newDiscount ;

                }

                orderSelcted.get().setPayment(newPAyment);
                orderSelcted.get().setTotalAmount(newTotalAmount);
                orderSelcted.get().setDiscount(newDiscount);

                Order orderUpated = orderRepository.save(orderSelcted.get()) ;

                carteItemUpdated.setOrder(orderUpated);


                return ResponseEntity.status(HttpStatus.OK).body(carteItemMapper.mapper(carteItemUpdated))   ;








            }
            else
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This carteItem don't have Order (Order not exist)")   ;
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This carteItem not exist !!") ;
        }
    }


    public ResponseEntity<?>  placerOrder(long idUser,long idOrder,String adress,String desription)
    {
        Optional<User> optionalUser = userServiceCustomer.findUserById(idUser) ;

        if (optionalUser.isPresent())
        {

            Optional<Order> optionalOrder = orderRepository.findById(idOrder)  ;

            if (optionalOrder.isPresent())
            {

                log.warn("value of getOrderStatus  >>>  " +optionalOrder.get().getOrderStatus());

                if (optionalOrder.get().getOrderStatus().equals(OrderStatus.Pending))
                {


                    if (optionalOrder.get().getTotalAmount()!=0f)
                    {
                        optionalOrder.get().setOrderStatus(OrderStatus.Placed);
                        optionalOrder.get().setOrderDescription(desription);
                        optionalOrder.get().setAdress(adress);



                        Order orderCreated =  orderRepository.save(optionalOrder.get()) ;

                        OrderDto orderDto = orderMapper.mapper(orderCreated)  ;

                        return ResponseEntity.status(HttpStatus.OK).body(orderDto)  ;
                    }
                    else
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Empty order , can't place order")  ;




                }
                else
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body( "(" + optionalOrder.get().getOrderStatus() + ")  --  " + "Status of this order not Pending : idOrder : "+idOrder)  ;

            }
            else
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Order not found for id : "+idOrder)  ;


        }
        else
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User not found for id : "+idUser)  ;

    }

    @Override
    public Optional<Order> findByIdAndDelivered(long idOrder) {
        return orderRepository.findByIdAndOrderStatus(idOrder,OrderStatus.Delivered)  ;
    }

    public Optional<Order> findById(long idOrder)
    {
        return orderRepository.findById(idOrder)  ;
    }


    public ResponseEntity<?> getOrderPending(long idUser)
    {
        Optional<Order> optionalOrder = orderRepository.findByOrderStatusAndUserId(OrderStatus.Pending,idUser)  ;

        if (optionalOrder.isPresent())
        {
            long idOrder = optionalOrder.get().getId() ;
            List<CarteItem> carteItems = carteItemService.findByOrderId(idOrder)  ;

            List<CarteItemDto> carteItemDtos = carteItemMapper.mapper((carteItems))  ;

            optionalOrder.get().setCarteItems(carteItems);

            OrderDto orderDtos = orderMapper.mapper(optionalOrder.get())  ;

            return ResponseEntity.status(HttpStatus.OK).body(carteItemDtos)  ;

        }
        else
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Not exist Pending order for this userID : "+idUser)  ;

    }

    public ResponseEntity<?> getOrderPendingForOrder(long idUser)
    {
        return ResponseEntity.ok(orderMapper.mapper(new HashSet<>( orderRepository.findAllByUserIdAndOrderStatus(idUser,OrderStatus.Pending))))  ;

    }



    public ResponseEntity<?> getOrderPlaced(long idUser)
    {



        return ResponseEntity.ok(orderMapper.mapper(new HashSet<>( orderRepository.findAllByUserIdAndOrderStatus(idUser,OrderStatus.Placed))))  ;

    }

    public ResponseEntity<?> getOrderShipped(long idUser)
    {
        return ResponseEntity.ok(orderMapper.mapper(new HashSet<>( orderRepository.findAllByUserIdAndOrderStatus(idUser,OrderStatus.Shipped))))  ;

    }

    public ResponseEntity<?> getOrderDelivered(long idUser)
    {

        return ResponseEntity.ok(orderMapper.mapper(new HashSet<>( orderRepository.findAllByUserIdAndOrderStatus(idUser,OrderStatus.Delivered))))  ;

    }

    @Override
    public Optional<Order> findByTrackingId(String trackingId) {
        return orderRepository.findByTrackingId(trackingId)  ;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order) ;
    }


}
