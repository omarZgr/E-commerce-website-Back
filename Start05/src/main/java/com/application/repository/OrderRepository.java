package com.application.repository;

import com.application.entity.Order;
import com.application.enums.OrderStatus;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findByOrderStatus(OrderStatus orderStatus);

    Optional<Order> findByOrderStatusAndUserId(OrderStatus orderStatus, long idUser);

    List<Order> findByUserIdAndOrderStatus(long idUser, OrderStatus orderStatus);

    Optional<Order> findByIdAndOrderStatus(long idOrder, OrderStatus orderStatus);

    Optional<Order> findByTrackingId(String trackingId);

    List<Order> findAllByTotalAmountNotAndOrderStatusOrderByOrderStatus(float totalAmount, OrderStatus orderStatus);
    List<Order> findAllByOrderStatus(OrderStatus orderStatus);


    @Query(value = "SELECT SUM(payment) FROM orders", nativeQuery = true)
    Double getTotalPayment();

    @Query(value = "SELECT SUM(total_amount) FROM orders", nativeQuery = true)
    Double getTotalTotalAmount();

    @Query(value = "SELECT COUNT(*) FROM orders WHERE coupon_id IS NOT NULL", nativeQuery = true)
    Long getCountOfRowsWithCoupon();

    List<Order> findAllByOrderStatusAndUserId(OrderStatus orderStatus, long idUser);

    List<Order> findAllByUserIdAndOrderStatus(long idUser, OrderStatus orderStatus);
}
