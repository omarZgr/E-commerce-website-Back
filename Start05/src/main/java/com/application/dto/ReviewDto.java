package com.application.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private long id ;

    private String description ;


    private byte[] img ;

    private ProductDto productDto  ;

    private UserDto userDto  ;

    private OrderDto orderDto  ;




}
