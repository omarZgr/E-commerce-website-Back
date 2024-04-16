package com.application.controller;

import com.application.dto.AuthentificationRequest;
import com.application.dto.SignupRequest;
import com.application.dto.UserDto;
import com.application.entity.User;
import com.application.repository.UserRepositroy;
import com.application.service.auth.AuthService;
import com.application.service.jwt.UserDetailsServiceImpl;
import com.application.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Log4j2
public class AutheController {

    private final AuthenticationManager authenticationManager ;
    private final UserDetailsServiceImpl userDetailsService ;
    private final UserRepositroy userReopository ;
    private final JwtUtil jwtUtil ;
    private final AuthService authService ;

    public static final String TOKEN_PREFIX = "Bearer " ;
    public static final String HEADER_STRING = "Authorization" ;

    @PostMapping("/authenticate")
    public void createAuthentificationToken(@RequestBody AuthentificationRequest authentificationRequest, HttpServletResponse response) throws IOException, JSONException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentificationRequest.getUsername(),authentificationRequest.getPassword()))  ;

        }catch (BadCredentialsException e)
        {
            throw  new BadCredentialsException("Incorrect username or password")  ;
        }

        String username = authentificationRequest.getUsername()  ;

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username) ;

        Optional<User> optionalUser = userReopository.findFirstByEmail(userDetails.getUsername()) ;

        final  String jwt = jwtUtil.generateToken(userDetails) ;

        if (optionalUser.isPresent())
        {
            response.getWriter().write(new JSONObject()
                    .put("userId",optionalUser.get().getId())
                    .put("role",optionalUser.get().getRole())
                    .toString()) ;
        }

        response.addHeader("Access-Control-Expose-Headers","Authorization");
        response.addHeader("Access-Control-Allow-Headers","Authorization,X-PINGOTHER,Origin, "
                +"X-Requested-With,Content-Type,Accept,X-Custom-header");

        response.addHeader(HEADER_STRING,TOKEN_PREFIX + jwt);
    }


    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@ModelAttribute UserDto userDto) throws IOException {

        log.warn("AM HERE -------------------");
        log.warn("userDTO --->> "+userDto.getUserName());



        return authService.createUserV2(userDto);

    }
}
