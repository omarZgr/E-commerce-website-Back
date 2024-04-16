package com.application.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id ;

    private String name ;
    private String code ;
    private  long discount ;


    private Date expirationDate ;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "id")
    private List<Order> orderList  ;}
