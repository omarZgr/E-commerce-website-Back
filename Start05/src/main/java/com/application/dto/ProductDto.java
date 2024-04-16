package com.application.dto;


import com.application.entity.Favoris;
import com.application.entity.Review;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

     private  long id ;

    private String name ;
    private String description ;
    private float price ;

    private byte[] byteImg ;



    private CategoryDto categoryDto ;

    private MultipartFile img ;






}
