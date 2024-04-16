package com.application.service.admin.carteItem;


import com.application.entity.CarteItem;
import com.application.entity.Order;
import com.application.mapper.carteItem.CarteItemMapper;
import com.application.repository.CarteItemRepository;
import com.application.repository.OrderRepository;
import com.application.service.admin.order.OrderAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarteItemAdminServiceImpl implements CarteItemAdminService{

    private final CarteItemRepository carteItemRepository  ;
    private final OrderRepository orderRepository ;
    private final CarteItemMapper carteItemMapper  ;
    @Override
    public List<CarteItem> findByOrderId(long idOrder) {
        return carteItemRepository.findByOrderId(idOrder)  ;
    }

    public ResponseEntity<?> findByOrderId_Controller(long idOrder) {

        Optional<Order> optionalOrder = orderRepository.findById(idOrder) ;

        if (optionalOrder.isPresent())
        {
            List<CarteItem> carteItems = carteItemRepository.findByOrderId(idOrder)  ;

            if (carteItems!=null)
            {

                    return ResponseEntity.ok(carteItemMapper.mapper(carteItems))  ;


            }else
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("this order don't have cart item 1" )  ;

        }
        else
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("this order not found")  ;


    }



    }
