package com.application.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id ;

    private String name ;
    private String description ;
    private float price ;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img ;

    @Column(columnDefinition = "boolean default false") // Set default value to false
    private boolean isDelete;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category ;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "id")
    private List<Faq> faqs  ;


    @OneToMany(mappedBy = "product",fetch = FetchType.EAGER)
    private List<Favoris> favoris ;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "id")
    private List<Review> reviews  ;




}
