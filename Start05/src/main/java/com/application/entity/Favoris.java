package com.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Favoris {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id ;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Product product  ;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private User user  ;

}
