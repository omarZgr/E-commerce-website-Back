package com.application.repository;

import com.application.entity.Favoris;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FavorisRepository extends JpaRepository<Favoris,Long> {
    Favoris findByProductId(long idProduct);

    @Query(" FROM Favoris f WHERE f.user.id = :idUser")
    List<Favoris> findByUserIdINFavoris(long idUser);

    List<Favoris> findAllByUserId(long idUser);

    Favoris findByProductIdAndUserId(long idProduct, long idUser);
}
