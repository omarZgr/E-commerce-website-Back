package com.application.repository;

import com.application.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    Optional<Review> findByProductIdAndUserId(long idProduct, long idUser);

    Optional<Review> findByProductIdAndUserIdAndOrderId(long idProduct, long idUser, long idOrder);

    List<Review> findByUserId(long idUser);
}
