package com.application.service.customer.review;

import com.application.dto.ReviewDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ReviewService {


    public ResponseEntity<?> getAllReview(long idUser)  ;

    public ResponseEntity<?> setReview(ReviewDto reviewDto) throws IOException;

    public ResponseEntity removeReview(long idUser,long idProduct,long idOrder)  ;
}
