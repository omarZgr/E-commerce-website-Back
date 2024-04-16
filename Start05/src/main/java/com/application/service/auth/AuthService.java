package com.application.service.auth;

import com.application.dto.SignupRequest;
import com.application.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AuthService {


    public ResponseEntity<?> createUserV2(UserDto userDto) throws IOException ;

        boolean hasUserWithEmail(String email);
}
