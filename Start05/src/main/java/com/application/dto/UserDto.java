package com.application.dto;


import com.application.entity.Favoris;
import com.application.entity.Review;
import com.application.enums.UserRole;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private  long id ;

    private String firstName ;
    private String lastName ;
    private String userName ;
    private String email ;
    private String password ;
    private Date dateCreation ;

    private byte[] byteImg ;
    private MultipartFile img ;

    private UserRole role ;


}
