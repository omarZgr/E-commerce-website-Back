package com.application.repository;

import com.application.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByName(String name);

    List<Product> findAllByNameContaining(String name);
    Optional<Product> findByIdAndName(long id,String name);

    List<Product> findAllByIsDelete(boolean isDelete);


}
