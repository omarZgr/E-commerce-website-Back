package com.application.mapper.carteItem;

import com.application.dto.CarteItemDto;
import com.application.entity.CarteItem;
import com.application.mapper.order.OrderMapper;
import com.application.mapper.product.ProductMapper;
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
public class CarteItemMapperImpl implements CarteItemMapper{

    private  OrderMapper orderMapper  ;
    private final ProductMapper productMapper ;

    @Autowired
    @Lazy
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }


    public CarteItemDto mapper(CarteItem carteItem)
    {

        CarteItemDto  carteItemDto = new CarteItemDto() ;

        carteItemDto.setId(carteItem.getId());
        carteItemDto.setPrice(carteItem.getPrice());
        carteItemDto.setQuantity(carteItem.getQuantity());

        if (carteItem.getOrder()!=null)
            carteItemDto.setOrderDto(orderMapper.mapper(carteItem.getOrder()));

        if (carteItem.getProduct()!=null)
            carteItemDto.setProductDto(productMapper.mapper(carteItem.getProduct()));

        return carteItemDto ;

    }

    public List<CarteItemDto> mapper(List<CarteItem> carteItems)
    {
        List<CarteItemDto>carteItemDtos = new ArrayList<>()  ;

        for (CarteItem carteItem:carteItems)
            carteItemDtos.add(mapper(carteItem))  ;

        return carteItemDtos  ;
    }



    public CarteItem unmapper(CarteItemDto carteItemDto) throws IOException {

        CarteItem  carteItem = new CarteItem() ;

        carteItem.setId(carteItemDto.getId());
        carteItem.setPrice(carteItemDto.getPrice());
        carteItem.setQuantity(carteItemDto.getQuantity());

        if (carteItemDto.getOrderDto()!=null)
            carteItem.setOrder(orderMapper.unmapper(carteItemDto.getOrderDto()));

        if (carteItemDto.getProductDto()!=null)
            carteItem.setProduct(productMapper.unmapper(carteItemDto.getProductDto()));

        return carteItem ;

    }

    public List<CarteItem> unmapper(List<CarteItemDto> carteItemDtos) throws IOException {
        List<CarteItem>carteItems = new ArrayList<>()  ;

        for (CarteItemDto carteItemDto:carteItemDtos)
            carteItems.add(unmapper(carteItemDto))  ;

        return carteItems  ;
    }



}
