package com.application.mapper.favoris;

import com.application.dto.FavorisDto;
import com.application.entity.Favoris;
import com.application.mapper.product.ProductMapper;
import com.application.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class FavorisMapperImpl implements FavorisMapper{

    private  UserMapper userMapper ;
    private  ProductMapper productMapper ;

    @Autowired
    @Lazy
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }



    @Autowired
    @Lazy
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    public FavorisDto mapper(Favoris favoris)
    {
        FavorisDto favorisDto = new FavorisDto() ;

        favorisDto.setId((favoris.getId()));

        if (favoris.getUser()!=null)
            favorisDto.setUserDto((userMapper.mapper(favoris.getUser())));

        if (favoris.getProduct()!=null)
            favorisDto.setProductDto((productMapper.mapper(favoris.getProduct())));

        return favorisDto ;

    }

    public List<FavorisDto> mapper(List<Favoris> favorisSet)
    {
        List<FavorisDto> favorisDtos = new ArrayList<>();

        for (Favoris favoris:favorisSet)
            favorisDtos.add(mapper(favoris)) ;

        return favorisDtos  ;
    }

    public Favoris unmapper(FavorisDto favorisDto) throws IOException {
        Favoris favoris = new Favoris() ;

        favoris.setId((favoris.getId()));

        if (favoris.getUser()!=null)
              favoris.setUser((userMapper.unmapper(favorisDto.getUserDto())));

        if (favoris.getProduct()!=null)
            favoris.setProduct((productMapper.unmapper(favorisDto.getProductDto())));

        return favoris ;

    }

    public List<Favoris> unmapper(List<FavorisDto>favorisDtos) throws IOException {
        List<Favoris> favorisSet = new ArrayList<>();

        for (FavorisDto favorisDto:favorisDtos)
            favorisSet.add(unmapper(favorisDto)) ;

        return favorisSet  ;
    }


}
