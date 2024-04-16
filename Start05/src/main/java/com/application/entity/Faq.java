package com.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Faq {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    private String question ;
    private String answer  ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product  ;


}
