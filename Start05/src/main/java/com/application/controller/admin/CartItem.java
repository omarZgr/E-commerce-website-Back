package com.application.controller.admin;

import com.application.service.admin.carteItem.CarteItemAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Log4j2
public class CartItem {

    private final CarteItemAdminService carteItemAdminService  ;

    @GetMapping("/cartItemOfOrder/{idOrder}")
    public ResponseEntity findByOrderId(@PathVariable long idOrder)
    {
        return carteItemAdminService.findByOrderId_Controller(idOrder) ;
    }


}
