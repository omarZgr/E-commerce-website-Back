package com.application.service.admin.product;

import com.application.dto.ProductDto;
import com.application.entity.Product;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Optional;

public interface ProductServiceAdmin {

    public ResponseEntity<?> addProduct(ProductDto productDto) throws IOException;
    public ResponseEntity<?> getProductById(long id) ;

    public ResponseEntity<?> getProductByName(String productName)   ;

        public Optional<Product> findById(long id)   ;
    public ResponseEntity<?> getAllProduct()  ;

    public ResponseEntity<?> updateProduct(ProductDto productDto) throws IOException;

    public ResponseEntity<?> deleteProduct(long productId);

}
