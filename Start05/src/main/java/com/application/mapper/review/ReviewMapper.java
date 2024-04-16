package com.application.mapper.review;

import com.application.dto.ReviewDto;
import com.application.entity.Review;

import java.io.IOException;
import java.util.List;

public interface ReviewMapper {

    public ReviewDto mapper(Review review)  ;

    public List<ReviewDto> mapper(List<Review> reviews)   ;

        public Review unmapper(ReviewDto reviewDto) throws IOException;

    public List<Review> unmapper(List<ReviewDto> reviewDtos) throws IOException;
    }
