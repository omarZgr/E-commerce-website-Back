package com.application.controller.customer;

import com.application.dto.ReviewDto;
import com.application.service.customer.review.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Log4j2
public class ReviewController {

    private final ReviewService reviewService  ;




    @GetMapping("/review/{idUser}")
    public ResponseEntity getAllReview(@PathVariable long idUser)
    {
        return reviewService.getAllReview(idUser)  ;
    }


    @PostMapping("/review")
    public ResponseEntity addReview(@RequestBody ReviewDto reviewDto) throws IOException {

        if (reviewDto!=null)
        {
            if (reviewDto.getOrderDto()!= null && reviewDto.getUserDto()!= null && reviewDto.getProductDto()!= null && reviewDto.getDescription()!= null && !reviewDto.getDescription().isEmpty()  )
                 return reviewService.setReview(reviewDto)  ;
            else
                return ResponseEntity.status(HttpStatus.CONFLICT).body("some attribute are empty !!");


        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("review  must be Not empty !!");

    }


    @DeleteMapping("/review/{idUser}/{idProduct}/{idOrder}")
    public ResponseEntity addReview(@PathVariable long idUser,@PathVariable long idProduct,@PathVariable long idOrder)
    {

        return reviewService.removeReview(idUser,idProduct,idOrder)   ;
    }
}
