package com.application.service.admin.user;

import com.application.dto.UserDto;
import com.application.entity.User;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Optional;

public interface UserServiceAdmin {


    public ResponseEntity<?> getAllCustomer()  ;

    public Optional<User> findUserById(long id)  ;


    ResponseEntity<?> changeProfile(UserDto userDto) throws IOException;
}
