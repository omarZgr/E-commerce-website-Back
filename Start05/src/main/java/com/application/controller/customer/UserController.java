package com.application.controller.customer;


import com.application.dto.UserDto;
import com.application.service.customer.user.UserServiceCustomer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserServiceCustomer userServiceCustomer  ;

    @PostMapping("/singup")
    public ResponseEntity<?> singUp(@RequestBody UserDto userDto) throws IOException {
        log.warn("AM HERE");

        if (userDto!=null)
        {
            return userServiceCustomer.signUP(userDto) ;
        }
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User must be Not empty !!");


    }


    @PostMapping("/changeProfile")
    public ResponseEntity<?> changeProfil(@ModelAttribute  UserDto userDto) throws IOException {
        log.warn("AM HERE");

        if (userDto!=null)
        {
            return userServiceCustomer.changeProfile(userDto) ;
        }
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User must be Not empty !!");


    }

    @GetMapping("/profile/{idUser}")
    public  ResponseEntity<?> getUser(@PathVariable long idUser)
    {
        return ResponseEntity.ok(userServiceCustomer.findUserById(idUser).get()) ;
    }
}
