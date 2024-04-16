package com.application.mapper.review;


import com.application.dto.ProductDto;
import com.application.dto.ReviewDto;
import com.application.entity.Product;
import com.application.entity.Review;
import com.application.mapper.order.OrderMapper;
import com.application.mapper.product.ProductMapper;
import com.application.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewMapperImpl implements ReviewMapper{

    private UserMapper userMapper ;
    private ProductMapper productMapper ;

    private final OrderMapper orderMapper ;

    @Autowired
    @Lazy
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Autowired
    @Lazy
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }


    public ReviewDto mapper(Review review)
    {
        ReviewDto reviewDto = new ReviewDto() ;

        reviewDto.setId(review.getId());
        reviewDto.setDescription(review.getDescription());
        reviewDto.setImg(review.getImg());

        if (review.getUser()!=null)
             reviewDto.setUserDto(userMapper.mapper(review.getUser()));

        if (review.getProduct()!=null)
            reviewDto.setProductDto(productMapper.mapper(review.getProduct()));


        if (review.getOrder()!=null)
            reviewDto.setOrderDto(orderMapper.mapper(review.getOrder()));

        return  reviewDto  ;
    }

    @Override
    public List<ReviewDto> mapper(List<Review> reviews) {


        List<ReviewDto> reviewDtos = new ArrayList<>();


        for(Review review:reviews)
        {
            reviewDtos.add(mapper(review))  ;
        }

        return reviewDtos  ;

    }

    public Review unmapper(ReviewDto reviewDto) throws IOException {
        Review review = new Review() ;

        review.setId(reviewDto.getId());
       review.setDescription(reviewDto.getDescription());
        review.setImg(reviewDto.getImg());

        if (reviewDto.getUserDto()!=null)
            review.setUser(userMapper.unmapper(reviewDto.getUserDto()));

        if (reviewDto.getProductDto()!=null)
            review.setProduct(productMapper.unmapper(reviewDto.getProductDto()));

        if (reviewDto.getOrderDto()!=null)
            review.setOrder(orderMapper.unmapper(reviewDto.getOrderDto()));



        return  review  ;
    }



    @Override
    public List<Review> unmapper(List<ReviewDto> reviewDtos) throws IOException {


        List<Review> reviews = new ArrayList<>();


        for(ReviewDto reviewDto:reviewDtos)
        {
            reviews.add(unmapper(reviewDto))  ;
        }

        return reviews  ;

    }


}
