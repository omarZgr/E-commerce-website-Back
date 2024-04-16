package com.application.mapper.user;

import com.application.dto.FavorisDto;
import com.application.dto.OrderDto;
import com.application.dto.ReviewDto;
import com.application.dto.UserDto;
import com.application.entity.Favoris;
import com.application.entity.Order;
import com.application.entity.Review;
import com.application.entity.User;
import com.application.mapper.favoris.FavorisMapper;
import com.application.mapper.order.OrderMapper;
import com.application.mapper.review.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper{



    public UserDto mapper(User user)
    {
        UserDto userDto = new UserDto()  ;

        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUserName(user.getUserName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setDateCreation(user.getDateCreation());
        userDto.setRole(user.getRole());


        if (user.getImg()!=null)
            userDto.setByteImg(user.getImg());

        return userDto  ;

    }

  public User unmapper(UserDto userDto) throws IOException {
      User user = new User()  ;

      user.setId(userDto.getId());
      user.setFirstName(userDto.getFirstName());
      user.setLastName(userDto.getLastName());
      user.setUserName(userDto.getUserName());
      user.setEmail(userDto.getEmail());
      user.setPassword(userDto.getPassword());
      user.setDateCreation(userDto.getDateCreation());
      user.setRole(userDto.getRole());


      if (userDto.getImg()!=null)
          user.setImg(userDto.getImg().getBytes());








      return user  ;




  }


}
