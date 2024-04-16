package com.application.dto;


import com.application.entity.Order;
import com.application.entity.Product;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarteItemDto {


    private long id ;
    private float price ;
    private int quantity ;

    private OrderDto orderDto ;

    private ProductDto productDto  ;

}
