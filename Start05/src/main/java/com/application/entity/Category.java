package com.application.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    private String name ;
    private String description ;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "id")
    private List<Product> products ;

}
