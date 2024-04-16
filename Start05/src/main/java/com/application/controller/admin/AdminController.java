package com.application.controller.admin;

import com.application.dto.UserDto;
import com.application.entity.User;
import com.application.repository.UserRepositroy;
import com.application.service.admin.user.UserServiceAdmin;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Log4j2
public class AdminController {

    private final UserServiceAdmin userServiceAdmin  ;
    private final UserRepositroy userRepositroy  ;

    @GetMapping("/users")
    public ResponseEntity<?> getAllCustomer()
    {
        return userServiceAdmin.getAllCustomer()  ;
    }

    @GetMapping("/userss")
    public List<User> getAll()
    {

        return userRepositroy.findAllV2()  ;

    }

    @PostMapping("/changeProfile")
    public ResponseEntity<?> changeProfil(@ModelAttribute UserDto userDto) throws IOException {
        log.warn("AM HERE");

        if (userDto!=null)
        {
            return userServiceAdmin.changeProfile(userDto) ;
        }
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User must be Not empty !!");


    }

    @GetMapping("/profile/{idUser}")
    public  ResponseEntity<?> getUser(@PathVariable long idUser)
    {
        return ResponseEntity.ok(userServiceAdmin.findUserById(idUser).get()) ;
    }
}
