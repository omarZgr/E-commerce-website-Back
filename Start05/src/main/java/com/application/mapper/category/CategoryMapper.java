package com.application.mapper.category;


import com.application.dto.CategoryDto;
import com.application.entity.Category;

import java.util.Set;

public interface CategoryMapper {

    CategoryDto mapper(Category category)  ;
    Set<CategoryDto> mapper(Set<Category> category)  ;

    Category unmapper(CategoryDto categoryDto)   ;
    Set<Category> unmapper(Set<CategoryDto> categoryDto)   ;
}
