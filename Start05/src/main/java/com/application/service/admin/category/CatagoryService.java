package com.application.service.admin.category;

import com.application.dto.CategoryDto;
import com.application.entity.Category;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface CatagoryService {

    public ResponseEntity<?> getAllCategory()  ;

    public ResponseEntity<?> addCategory(CategoryDto categoryDto)  ;

    public Optional<Category> findById(long id)  ;
}
