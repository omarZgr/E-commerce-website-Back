package com.application.entity;

import com.application.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    private String adress ;
    private String orderDescription ;
    private float discount ;
    private float payment ;
    private float totalAmount ;
    private String trackingId ;

    private OrderStatus orderStatus ;


    private Date dateCreation ;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "id")
    private List<CarteItem> carteItems ;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
   private User user  ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couponId")
    @JsonIgnore
    private Coupon coupon ;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "id")
    private List<Review> reviews  ;

}
