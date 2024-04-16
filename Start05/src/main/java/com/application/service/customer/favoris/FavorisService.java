package com.application.service.customer.favoris;

import org.springframework.http.ResponseEntity;

public interface FavorisService {


    public ResponseEntity<?> addToFavoris(long idProduct, long idUser)  ;

    public ResponseEntity<?> removeFromFavoris(long idProduct,long idUser)  ;

    public ResponseEntity<?> getAllFavoris(long idUser)  ;

    ResponseEntity<?> getFavorisOfUser(long idUser) ;

}
