package com.application.service.customer.user;


import com.application.dto.UserDto;
import com.application.entity.User;
import com.application.enums.UserRole;
import com.application.mapper.user.UserMapper;
import com.application.repository.UserRepositroy;
import com.application.service.customer.order.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceCustomerImpl implements UserServiceCustomer{

    private final UserRepositroy userRepositroy  ;
    private final UserMapper userMapper;


    @Autowired
    private PasswordEncoder passwordEncoder;

    private  OrderService orderService  ;

    @Autowired
    @Lazy
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }



    public Optional<User> findUserById(long id)
    {
        return userRepositroy.findById(id)  ;
    }

    public ResponseEntity<?> signUP(UserDto userDto) throws IOException {

        log.warn("(DTO) UserNae >>> "+userDto.getUserName());
        User user = userMapper.unmapper(userDto) ;

        log.warn("(Entity) userNmae >>> "+user.getUserName());

        if (!userRepositroy.findByEmail(userDto.getEmail()).isPresent())
        {
            if (!userRepositroy.findByUserName(userDto.getUserName()).isPresent())
            {

                user.setRole(UserRole.CUSTOMER);
                user.setDateCreation(new Date());
                User userCreated  = userRepositroy.save(user)  ;

             //   orderService.initOrder(userCreated)  ;

                return  ResponseEntity.status(HttpStatus.CREATED).body(userMapper.mapper(userCreated))  ;

            }
            else
            {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("This userName already used !!")  ;

            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This email already used !!")  ;
        }

    }

    public ResponseEntity changeProfile(UserDto userDto) throws IOException {

        Optional<User> optionalUseruser  = userRepositroy.findById(userDto.getId()) ;

        log.warn("(dto) userNmae >>> "+userDto.getUserName());

        if(optionalUseruser.isPresent())
        {
            User user = optionalUseruser.get()  ;
            if (userRepositroy.findById(user.getId()).isPresent())
            {
                log.warn("USER INSERT >>>" +user.getUserName());

                if (!userRepositroy.findByUserNameForOtherUser(userDto.getId(),userDto.getUserName()).isPresent())
                {
                    if (!userRepositroy.findByEmailForOtherUser(userDto.getId(),userDto.getEmail()).isPresent())
                    {
                        userDto.setRole(user.getRole());
                        userDto.setDateCreation(user.getDateCreation());
                        userDto.setPassword(getEncodedPassword(userDto.getPassword()));

                        return ResponseEntity.ok(userMapper.mapper(userRepositroy.save(userMapper.unmapper(userDto))))  ;

                    }
                    else

                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This email already used !!")  ;



                }
                else
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("This userName already used !!")  ;




            }

            else
            {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User not found , idUser :: "+userDto.getId())  ;
            }
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This user not foud for id: "+userDto.getId()) ;

    }


    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }


}
