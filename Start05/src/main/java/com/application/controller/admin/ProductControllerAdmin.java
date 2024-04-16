package com.application.controller.admin;


import com.application.dto.ProductDto;
import com.application.service.admin.product.ProductServiceAdmin;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Log4j2
public class ProductControllerAdmin {

    private final ProductServiceAdmin productService;




    @GetMapping("/products")
    public ResponseEntity<?> getAllProduct() {

        return productService.getAllProduct() ;

    }


    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable long productId) {

            return productService.getProductById(productId) ;


    }

    @GetMapping("/product/search/{productName}")
    public ResponseEntity<?> getProductByName(@PathVariable String productName) {

        return productService.getProductByName(productName) ;


    }


    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@ModelAttribute ProductDto productDto) throws IOException {

        log.warn("productDto >>>"+productDto);

        if (productDto!=null && productDto.getName() !=null)
        {
                return productService.addProduct(productDto) ;


        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product was be not Null")  ;
    }

    @PutMapping(value = "/product/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProduct(@ModelAttribute ProductDto productDto) throws IOException {
        log.warn("productDto >>>" + productDto);

        if (productDto != null && productDto.getName() != null) {
            return productService.updateProduct(productDto);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product must not be null");
        }
    }


    @DeleteMapping("/product/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable long productId)
    {
        return productService.deleteProduct(productId)  ;
    }



}
