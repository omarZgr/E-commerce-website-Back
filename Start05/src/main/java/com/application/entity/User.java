package com.application.entity;

import com.application.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id ;

    private String firstName ;
    private String lastName ;
    private String userName ;
    private String email ;
    private String password ;
    private Date dateCreation ;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img ;

    private UserRole role ;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "id")
    private List<Order> orderList  ;

    @OneToMany(mappedBy = "user")
    private List<Favoris> favoris ;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "id")
    private List<Review> reviews  ;
}
