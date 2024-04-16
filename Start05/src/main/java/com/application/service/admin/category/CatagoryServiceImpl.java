package com.application.service.admin.category;


import com.application.dto.CategoryDto;
import com.application.entity.Category;
import com.application.exception.HandleException;
import com.application.mapper.category.CategoryMapper;
import com.application.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CatagoryServiceImpl implements CatagoryService{

    private final CategoryRepository categoryRepository;
    private  final CategoryMapper categoryMapper ;

    public ResponseEntity<?> getAllCategory()
    {
        List<Category> categories = categoryRepository.findAll()  ;

        if (categories.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.OK).body("Categories Empty");

        }
        else
        {
            return ResponseEntity.status(HttpStatus.OK).body(categoryMapper.mapper(new HashSet<>(categories)));

        }
    }

    public ResponseEntity<?> addCategory(CategoryDto categoryDto)
    {

        log.warn("VALUE OF CategoryDto >>>>>>>>> "+categoryDto);
        Category category = categoryMapper.unmapper(categoryDto) ;

        log.warn("VALUE OF category >>>>>>>>> "+category);


        if (!categoryRepository.findByName(categoryDto.getName()).isPresent())
        {
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryMapper.mapper(categoryRepository.save(category)))  ;
        }
        else
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new HandleException("This Category name is already exist!"))  ;
        }
    }

    public Optional<Category> findById(long id)
    {
        return categoryRepository.findById(id)  ;
    }


}
