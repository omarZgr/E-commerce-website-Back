package com.application.controller.customer;

import com.application.service.customer.product.ProductServiceCustomer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Log4j2
public class ProductControllerCustomer {

    private final ProductServiceCustomer productService;


    @GetMapping("/products")
    public ResponseEntity<?> getAllProduct() {

        return productService.getAllProduct() ;

    }


    @GetMapping("/product/search/{productName}")
    public ResponseEntity<?> getProductByName(@PathVariable String productName) {

        return productService.getProductByName(productName) ;


    }


    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable long productId) {

        return productService.getProductById(productId) ;


    }


}
