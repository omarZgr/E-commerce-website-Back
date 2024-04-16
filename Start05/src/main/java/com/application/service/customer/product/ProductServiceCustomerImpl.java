package com.application.service.customer.product;

import com.application.dto.ProductDto;
import com.application.entity.Product;
import com.application.exception.HandleException;
import com.application.mapper.product.ProductMapper;
import com.application.repository.ProductRepository;
import com.application.service.admin.category.CatagoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceCustomerImpl implements ProductServiceCustomer{


    private final ProductRepository productRepository ;
    private final CatagoryService catagoryService ;

    private final ProductMapper productMapper ;


    @Override
    public ResponseEntity<?> getAllProduct()
    {
        List<Product> products = productRepository.findAll()  ;

        if (products.isEmpty())
        {
            log.warn("products >>>> "+products.toString());
            return ResponseEntity.status(HttpStatus.OK).body("Products Empty");

        }
        else
        {
             List<ProductDto> productDtos = productMapper.mapper((products))  ;
            return ResponseEntity.status(HttpStatus.OK).body(products);

        }
    }

    @Override
    public ResponseEntity<?> getProductByName(String productName) {
        log.warn("product name >> "+productName);
        List<Product> optionalProduct = productRepository.findAllByNameContaining(productName);

        if (optionalProduct!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(productMapper.mapper(optionalProduct))  ;
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new HandleException("Product not found for id: " + productName)) ;
        }
    }
    @Override
    public ResponseEntity<?> getProductById(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(productMapper.mapper(optionalProduct.get()))  ;
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new HandleException("Product not found for id: " + id)) ;
        }
    }

    @Override
    public Optional<Product> findById(long id) {
        return productRepository.findById(id) ;
    }

}
