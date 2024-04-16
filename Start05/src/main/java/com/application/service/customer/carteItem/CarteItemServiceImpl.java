package com.application.service.customer.carteItem;


import com.application.entity.CarteItem;
import com.application.entity.Order;
import com.application.mapper.order.OrderMapper;
import com.application.mapper.product.ProductMapper;
import com.application.repository.CarteItemRepository;
import com.application.service.customer.order.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CarteItemServiceImpl implements CarteItemService{

    private final CarteItemRepository carteItemRepository ;
    private  OrderServiceImpl orderService  ;

    @Autowired
    @Lazy
    public void setOrderServiceImpl(OrderServiceImpl orderServiceImpl) {
        this.orderService = orderServiceImpl;
    }
    ;

    private final OrderMapper orderMapper  ;

    public CarteItem insert(CarteItem carteItem)
    {

        return carteItemRepository.save(carteItem) ;
    }

    public Optional<CarteItem> findByProductId(long idProduct)
    {
        return carteItemRepository.findByProductId(idProduct) ;
    }

    @Override
    public CarteItem save(CarteItem carteItem) {

        return carteItemRepository.save(carteItem)  ;
    }

    @Override
    public Optional<CarteItem> findById(long idCarteItem) {
        return carteItemRepository.findById(idCarteItem)  ;
    }

    public float getSumPriceOfCarteItem(long orderId)
    {
        return carteItemRepository.getTotalPrice(orderId)  ;
    }

    @Override
    public List<CarteItem> findByOrderId(long idOrder) {
        return carteItemRepository.findByOrderId(idOrder)  ;
    }

    @Override
    public Optional<Object> findByProductIdAndOrderIdNot(long idProduct, long idOrder) {
        return carteItemRepository.findByProductIdAndOrderIdNot(idProduct,idOrder) ;
    }

    @Override
    public int getQuantityInCart(long idCard) {
        return carteItemRepository.findById(idCard).get().getQuantity();

    }

    public ResponseEntity<?> deleteFromCart(long idCard)
    {
        Optional<CarteItem> optionalCarteItem = carteItemRepository.findById(idCard) ;

        if (optionalCarteItem.isPresent())
        {
            Optional<Order> optionalOrder = orderService.findById(optionalCarteItem.get().getOrder().getId()) ;

            if (optionalOrder.isPresent())
            {
                float totalAmountOld = optionalOrder.get().getTotalAmount() ;
                float paymentOld = optionalOrder.get().getPayment() ;
                float discountOld = optionalOrder.get().getDiscount() ;

                float totalAmountNew= totalAmountOld - optionalCarteItem.get().getPrice() ;
                float  paymentNew =totalAmountNew ;

                float newDiscount = 0f ;

                float totalPriceOfCartItem = getSumPriceOfCarteItem(optionalOrder.get().getId())  ;

                if (optionalOrder.get().getCoupon()!=null)
                    log.warn("AM HERE AND THIS IS VALUE OF COUPON >>>>> "+optionalOrder.get().getCoupon().getId());

                else
                    log.warn("AM HERE AND THIS IS VALUE OF COUPON >>>>>  NULL");

                if (optionalOrder.get().getCoupon()!=null)
                {
                    long discount = optionalOrder.get().getCoupon().getDiscount()  ;

                    newDiscount = (float) discount / 100.0f * totalAmountNew ;
                    paymentNew = totalPriceOfCartItem - newDiscount ;

                    log.warn("RANI DKHLT U VALUE OF newDiscount >>>  "+newDiscount);

                }

                optionalOrder.get().setPayment(paymentNew);
                optionalOrder.get().setTotalAmount(totalAmountNew);
                optionalOrder.get().setDiscount(newDiscount);

                Order orderUpated = orderService.save(optionalOrder.get()) ;

                carteItemRepository.deleteById(idCard);


                return ResponseEntity.status(HttpStatus.OK).body(orderMapper.mapper(orderUpated))   ;

            }
            else
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This order not exist !! ")  ;
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This cad not exist for id : "+idCard) ;


    }
}
