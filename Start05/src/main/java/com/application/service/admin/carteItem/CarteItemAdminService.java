package com.application.service.admin.carteItem;

import com.application.entity.CarteItem;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CarteItemAdminService {

    List<CarteItem> findByOrderId(long idOrder);

    public ResponseEntity<?> findByOrderId_Controller(long idOrder) ;


    }
