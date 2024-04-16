package com.application.mapper.category;

import com.application.dto.CategoryDto;
import com.application.dto.ProductDto;
import com.application.entity.Category;
import com.application.entity.Product;
import com.application.mapper.product.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryMapperImpl implements CategoryMapper{




    @Override
    public CategoryDto mapper(Category category) {
        CategoryDto categoryDto = new CategoryDto() ;

        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());




        return categoryDto ;
    }

    @Override
    public Set<CategoryDto> mapper(Set<Category> categories) {

        Set<CategoryDto> categoryDtos = new HashSet<>();

        for(Category category:categories)
        {
            categoryDtos.add(mapper(category))  ;
        }

        return categoryDtos  ;

    }

    @Override
    public Category unmapper(CategoryDto categoryDto) {
        Category category = new Category() ;

        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());





        return category ;    }

    @Override
    public Set<Category> unmapper(Set<CategoryDto> categoryDtos) {


        Set<Category> categories = new HashSet<>();

        for(CategoryDto categoryDto:categoryDtos)
        {
            categories.add(unmapper(categoryDto))  ;
        }

        return categories  ;
    }
}
