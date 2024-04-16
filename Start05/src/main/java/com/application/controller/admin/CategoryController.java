package com.application.controller.admin;


import com.application.dto.CategoryDto;
import com.application.service.admin.category.CatagoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Log4j2
public class CategoryController {

    private final CatagoryService catagoryService ;


    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {

        return catagoryService.getAllCategory() ;

    }

    @PostMapping("/category")
    public ResponseEntity<?> addProduct(@RequestBody CategoryDto categoryDto)
    {

        if (categoryDto!=null && categoryDto.getName() !=null)
        {
            return catagoryService.addCategory(categoryDto) ;


        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category was be not Null")  ;
    }







}
