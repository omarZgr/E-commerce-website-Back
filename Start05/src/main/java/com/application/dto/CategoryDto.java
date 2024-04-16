package com.application.dto;


import com.application.entity.Product;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private long id ;
    private String name ;
    private String description ;


}
