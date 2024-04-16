package com.application.service.auth;

import com.application.dto.LoginRequest;
import com.application.dto.SignupRequest;
import com.application.dto.UserDto;
import com.application.entity.Order;
import com.application.entity.User;
import com.application.enums.OrderStatus;
import com.application.enums.UserRole;
import com.application.mapper.user.UserMapper;
import com.application.repository.OrderRepository;
import com.application.repository.UserRepositroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthServiceImpl implements  AuthService{

    private final UserRepositroy userReopository ;

    private final UserRepositroy userRepositroy  ;
    private final UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public ResponseEntity<?> createUserV2(UserDto userDto) throws IOException {

        log.warn("(DTO) UserNae >>> "+userDto.getUserName());
        User user = userMapper.unmapper(userDto) ;

        log.warn("(Entity) userNmae >>> "+user.getUserName());

        if (!userRepositroy.findByEmail(userDto.getEmail()).isPresent())
        {
            if (!userRepositroy.findByUserName(userDto.getUserName()).isPresent())
            {

                user.setRole(UserRole.CUSTOMER);
                user.setDateCreation(new Date());
                user.setPassword(getEncodedPassword(userDto.getPassword()));

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

    public UserDto login(LoginRequest loginRequest)
    {

       Optional<User> userOptional = userReopository.findByEmailAndPassword(loginRequest.getEmail(),loginRequest.getPassword()) ;

        if (!userOptional.isPresent())
        {
            throw new UsernameNotFoundException("Not Found") ;
        }
        else

        return null;
    }


    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }


    public boolean hasUserWithEmail(String email)
    {
        return userReopository.findFirstByEmail(email).isPresent()  ;
    }


    @PostConstruct
    public void createAdminAccount()
    {
        User adminAccount = userReopository.findByRole(UserRole.ADMIN) ;

        if (adminAccount == null)
        {
            User user = new User()  ;

            user.setEmail("admin@test.com");
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setUserName("admin");
            user.setPassword(getEncodedPassword("admin"));
            user.setRole(UserRole.ADMIN);
            user.setDateCreation(new Date());

            User userCreated = userReopository.save(user)  ;


        }
    }


}
