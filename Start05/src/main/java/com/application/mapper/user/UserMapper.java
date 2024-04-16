package com.application.mapper.user;


import com.application.dto.UserDto;
import com.application.entity.User;

import java.io.IOException;


public interface UserMapper {

    public UserDto mapper(User user)  ;
    public User unmapper(UserDto userDto) throws IOException;

}
