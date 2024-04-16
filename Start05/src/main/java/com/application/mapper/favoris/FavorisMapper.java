package com.application.mapper.favoris;

import com.application.dto.FavorisDto;
import com.application.entity.Favoris;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface FavorisMapper {

    public FavorisDto mapper(Favoris favoris)  ;

    public List<FavorisDto> mapper(List<Favoris>favorisSet)  ;

    public Favoris unmapper(FavorisDto favorisDto) throws IOException;

    public List<Favoris> unmapper(List<FavorisDto>favorisDtos) throws IOException;

}
