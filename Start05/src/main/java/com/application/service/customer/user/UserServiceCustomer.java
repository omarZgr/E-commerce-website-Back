package com.application.service.customer.user;

import com.application.dto.UserDto;
import com.application.entity.User;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Optional;

public interface UserServiceCustomer {

    public Optional<User> findUserById(long id)  ;

    public ResponseEntity<?> signUP(UserDto userDto) throws IOException;

    public ResponseEntity changeProfile(UserDto userDto) throws IOException;
}
