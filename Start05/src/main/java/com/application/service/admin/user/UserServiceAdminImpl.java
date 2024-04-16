package com.application.service.admin.user;


import com.application.dto.UserDto;
import com.application.entity.User;
import com.application.mapper.user.UserMapper;
import com.application.repository.UserRepositroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceAdminImpl implements UserServiceAdmin{

    private final UserRepositroy userRepositroy  ;
    private final UserMapper userMapper  ;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public ResponseEntity<?> getAllCustomer()
    {

        List<User> users = userRepositroy.findAll()  ;

        if (users.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.OK).body("users Empty");

        }
        else
        {
            return ResponseEntity.status(HttpStatus.OK).body(users);

        }

    }

    public Optional<User> findUserById(long id)
    {
        return userRepositroy.findById(id)  ;
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
                        log.warn("password new :: " + userDto.getPassword());
                        String hashPasword = getEncodedPassword(userDto.getPassword())  ;
                        log.warn("password new hashing :: " +hashPasword);
                        userDto.setPassword(hashPasword);


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
