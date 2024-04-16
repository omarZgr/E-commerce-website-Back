package com.application.service.jwt;

import com.application.entity.User;
import com.application.repository.UserRepositroy;
import com.application.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepositroy userReopository ;
    private final AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> adminOptional = userReopository.findFirstByEmail(username) ;

        if (!adminOptional.isPresent())
        {
            throw new UsernameNotFoundException("This userName : "+username+" - Not Found !!")  ;
        }
        else
        {
            return new org.springframework.security.core.userdetails.User(
                    adminOptional.get().getEmail(),
                    adminOptional.get().getPassword(),
                    new ArrayList<>());
        }
    }
}
