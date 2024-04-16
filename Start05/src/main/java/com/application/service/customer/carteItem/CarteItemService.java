package com.application.service.customer.carteItem;

import com.application.entity.CarteItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface CarteItemService {

    public CarteItem insert(CarteItem carteItem)  ;

    public Optional<CarteItem> findByProductId(long idProduct)  ;

    public CarteItem save(CarteItem carteItem);

    Optional<CarteItem> findById(long idCarteItem);

    public float getSumPriceOfCarteItem(long orderId)  ;

    List<CarteItem> findByOrderId(long idOrder);

    Optional<Object> findByProductIdAndOrderIdNot(long idProduct, long idOrder);

    public int getQuantityInCart( long idCard)  ;
    public ResponseEntity<?> deleteFromCart(long idCard)  ;


}
