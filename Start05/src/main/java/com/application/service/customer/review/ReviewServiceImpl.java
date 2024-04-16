package com.application.service.customer.review;


import com.application.dto.ReviewDto;
import com.application.entity.*;
import com.application.enums.OrderStatus;
import com.application.mapper.order.OrderMapper;
import com.application.mapper.product.ProductMapper;
import com.application.mapper.review.ReviewMapper;
import com.application.mapper.user.UserMapper;
import com.application.repository.ReviewRepository;
import com.application.service.customer.order.OrderService;
import com.application.service.customer.product.ProductServiceCustomer;
import com.application.service.customer.user.UserServiceCustomer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository  ;
    private final ProductServiceCustomer productServiceCustomer  ;

    private final OrderService orderService  ;
    private final UserServiceCustomer userServiceCustomer  ;
    private final ReviewMapper reviewMapper  ;

    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;


    public ResponseEntity<?> getAllReview(long idUser)
    {
        Optional<User>optionalUser = userServiceCustomer.findUserById(idUser)  ;

        if (optionalUser.isPresent())
        {
            List<Review> reviews = reviewRepository.findByUserId(idUser)  ;

            if (!reviews.isEmpty())
            {
                List<ReviewDto> reviewDtos = reviewMapper.mapper(reviews)  ;

                return ResponseEntity.ok(reviewDtos)  ;
            }
            else
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("You don't have any reviews")  ;


        }

        else
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("This user not exist , idUser :" +idUser)  ;

    }

    public ResponseEntity setReview(ReviewDto reviewDto) throws IOException {

    long idUser   = reviewDto.getUserDto().getId();

    long idProduct = reviewDto.getProductDto().getId()  ;

    long idOrder = reviewDto.getOrderDto().getId() ;

        Optional<User>optionalUser = userServiceCustomer.findUserById(idUser)  ;

        if (optionalUser.isPresent())
        {
            Optional<Product> optionalProduct = productServiceCustomer.findById(idProduct)  ;

            if (optionalProduct.isPresent())
            {

                Optional<Order> optionalOrder = orderService.findById(idOrder)  ;


                if (optionalOrder.isPresent())
                {

                    Optional<Review> reviewOptional = reviewRepository.findByProductIdAndUserIdAndOrderId(idProduct,idUser,idOrder) ;

                    if (!reviewOptional.isPresent()) {

                        boolean isHaveOrderDelivered = orderService.findByIdAndDelivered(idOrder).get().getOrderStatus().equals(OrderStatus.Delivered);

                        log.warn("orderService.findByIdAndDelivered(idOrder) >> " + orderService.findByIdAndDelivered(idOrder).get().getOrderStatus());

                        if (isHaveOrderDelivered) {

                                Review review = reviewMapper.unmapper(reviewDto);

                                ReviewDto reviewDtoCreated = reviewMapper.mapper(reviewRepository.save(review));

                            reviewDtoCreated.setProductDto(productMapper.mapper(optionalProduct.get()));
                            reviewDtoCreated.setOrderDto(orderMapper.mapper(optionalOrder.get()));
                            reviewDtoCreated.setUserDto(userMapper.mapper(optionalUser.get()));

                                return ResponseEntity.status(HttpStatus.CREATED).body(reviewDtoCreated);





                        }
                        else
                            return ResponseEntity.status(HttpStatus.CONFLICT).body("You don't have order shipped , can't access to review !!")    ;





                    }
                    else
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("already Review for product id : "+idProduct+"  for this Order id : "+idOrder)  ;


                }
                else
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("This order not exist , idOrder :" +idOrder)  ;

            }
            else
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("This product not exist , idProduct :" +idProduct)  ;

        }
        else
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("This user not exist , idUser :" +idUser)  ;

    }


    public ResponseEntity removeReview(long idUser,long idProduct,long idOrder)
    {

        Optional<User>optionalUser = userServiceCustomer.findUserById(idUser)  ;

        if (optionalUser.isPresent())
        {
            Optional<Product> optionalProduct = productServiceCustomer.findById(idProduct)  ;

            if (optionalProduct.isPresent())
            {

                Optional<Order> optionalOrder = orderService.findById(idOrder)  ;


                if (optionalOrder.isPresent())
                {

                    Optional<Review> review = reviewRepository.findByProductIdAndUserIdAndOrderId(idProduct,idUser,idOrder) ;

                    if (review.isPresent())
                    {
                        reviewRepository.delete(review.get());
                        return ResponseEntity.status(HttpStatus.OK).body("Deleted Review Successfully  !!!");
                    }
                    else
                        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Review doesn't exist" )  ;


                }
                else
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("This order not exist , idOrder :" +idOrder)  ;

            }
            else
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("This product not exist , idProduct :" +idProduct)  ;

        }
        else
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("This user not exist , idUser :" +idUser)  ;

    }
}
