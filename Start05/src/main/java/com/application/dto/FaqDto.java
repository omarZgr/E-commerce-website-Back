package com.application.dto;


import com.application.entity.Product;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FaqDto {

    private long id ;
    private String question ;
    private String answer  ;

    private ProductDto productDto;


}
