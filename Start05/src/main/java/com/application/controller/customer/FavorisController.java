package com.application.controller.customer;

import com.application.service.customer.favoris.FavorisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Log4j2
public class FavorisController {

    private final FavorisService favorisService ;


    @GetMapping("/favoris/{idProduct}/{idUser}")
    private ResponseEntity<?> addToFavoris(@PathVariable long idProduct, @PathVariable long idUser)
    {
        return favorisService.addToFavoris(idProduct,idUser)  ;
    }

    @DeleteMapping("/favoris/{idProduct}/{idUser}")
    private ResponseEntity<?> deleteFromFavoris(@PathVariable long idProduct,@PathVariable long idUser)
    {
        return favorisService.removeFromFavoris(idProduct,idUser)  ;
    }


    @GetMapping("/favoris/{idUser}")
    private ResponseEntity<?> getAllFavoris(@PathVariable long idUser)
    {
        return favorisService.getAllFavoris(idUser)  ;
    }


    public ResponseEntity<?> getFavorisOfUser(@PathVariable long idUser)
    {
        return favorisService.getFavorisOfUser(idUser) ;
    }
}
