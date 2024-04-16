package com.application.controller.guest;

import com.application.service.guest.GuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tracker")
@RequiredArgsConstructor
@Log4j2
public class GuestController {

    private final GuestService guestService  ;

    @GetMapping("/{trackerId}")
    public ResponseEntity trackOrder(@PathVariable String trackerId)
    {

        if (trackerId!=null || !trackerId.isEmpty())
            return guestService.trackOrder(trackerId) ;

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("trackOrder must be not empty !!")   ;
    }
}
