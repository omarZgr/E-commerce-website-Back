package com.application.repository;

import com.application.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {
    Optional<Coupon> findByName(String name);

    List<Coupon> findAllByOrderByExpirationDateDesc();

    Optional<Coupon> findByCode(String code);
}
