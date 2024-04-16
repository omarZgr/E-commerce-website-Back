package com.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarteItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    private float price ;
    private int quantity ;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    @JsonIgnore
   private Order order ;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product  ;
}
