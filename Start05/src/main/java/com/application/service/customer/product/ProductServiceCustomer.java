package com.application.service.customer.product;

import com.application.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ProductServiceCustomer {

    public ResponseEntity<?> getProductById(long id) ;

    public Optional<Product> findById(long id)   ;
    public ResponseEntity<?> getAllProduct()  ;

    public ResponseEntity<?> getProductByName(String productName)   ;

}
