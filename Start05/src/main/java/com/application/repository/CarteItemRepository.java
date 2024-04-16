package com.application.repository;

import com.application.entity.CarteItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CarteItemRepository extends JpaRepository<CarteItem,Long> {
    Optional<CarteItem> findByProductId(long idProduct);

    @Query("SELECT SUM(ci.price) FROM CarteItem ci WHERE ci.order.id = :orderId")
    float getTotalPrice(long orderId);

    List<CarteItem> findByOrderId(long idOrder);

    Optional<Object> findByProductIdAndOrderIdNot(long idProduct, long idOrder);
}
