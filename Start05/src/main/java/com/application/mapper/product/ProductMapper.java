package com.application.mapper.product;


import com.application.dto.ProductDto;
import com.application.entity.Product;

import java.io.IOException;
import java.util.List;


public interface ProductMapper {

    ProductDto mapper(Product product)  ;
    List<ProductDto> mapper(List<Product> product)  ;

    Product unmapper(ProductDto productDto) throws IOException;
    List<Product> unmapper(List<ProductDto> productDto) throws IOException;


}
