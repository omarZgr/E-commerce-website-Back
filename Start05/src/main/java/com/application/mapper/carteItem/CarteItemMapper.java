package com.application.mapper.carteItem;

import com.application.dto.CarteItemDto;
import com.application.entity.CarteItem;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CarteItemMapper {

    public CarteItemDto mapper(CarteItem carteItem)  ;

    public List<CarteItemDto> mapper(List<CarteItem> carteItems)  ;

    public CarteItem unmapper(CarteItemDto carteItemDto) throws IOException;

    public List<CarteItem> unmapper(List<CarteItemDto> carteItemDtos) throws IOException;
}
