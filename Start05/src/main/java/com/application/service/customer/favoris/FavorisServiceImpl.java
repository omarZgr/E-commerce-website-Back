package com.application.service.customer.favoris;


import com.application.dto.FavorisDto;
import com.application.entity.Favoris;
import com.application.entity.Product;
import com.application.entity.User;
import com.application.mapper.favoris.FavorisMapper;
import com.application.repository.FavorisRepository;
import com.application.repository.UserRepositroy;
import com.application.service.customer.product.ProductServiceCustomer;
import com.application.service.customer.user.UserServiceCustomer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class FavorisServiceImpl implements FavorisService{

    private final FavorisRepository favorisRepository  ;
    private UserServiceCustomer userServiceCustomer  ;

    private final ProductServiceCustomer productServiceCustomer  ;

    private final FavorisMapper favorisMapper ;

    @Autowired
    @Lazy
    public void setUserServiceCustomer(UserServiceCustomer userServiceCustomer) {
        this.userServiceCustomer = userServiceCustomer;
    }


    public ResponseEntity<?> addToFavoris(long idProduct, long idUser)
    {
        Optional<Product> optionalProduct = productServiceCustomer.findById(idProduct)  ;

        if (optionalProduct.isPresent())
        {
            Favoris optionalFavoris = favorisRepository.findByProductIdAndUserId(idProduct,idUser)  ;

            if (optionalFavoris==null)
            {
                Optional<User> optionalUser = userServiceCustomer.findUserById(idUser)  ;

                if (optionalUser.isPresent())
                {

                    Favoris favoris = new Favoris() ;

                    favoris.setProduct(optionalProduct.get());
                    favoris.setUser(optionalUser.get());

                    favorisRepository.save(favoris)  ;

                    return ResponseEntity.status(HttpStatus.OK).body("Add to Favoris Successfully")  ;
                }
                else {
                    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("This user doesn't exist !!")  ;
                }



            }
            else
            {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("This product already favorit !!")  ;

            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This product nit exist !!")  ;
        }
    }

    public ResponseEntity<?> removeFromFavoris(long idProduct,long idUser)
    {
        Optional<Product> optionalProduct = productServiceCustomer.findById(idProduct)  ;

        if (optionalProduct.isPresent())
        {
            Favoris optionalFavoris = favorisRepository.findByProductIdAndUserId(idProduct,idUser)  ;


            if (optionalFavoris!=null)
            {
                Optional<User> optionalUser = userServiceCustomer.findUserById(idUser)  ;

                if (optionalUser.isPresent())
                {
                    favorisRepository.deleteById(optionalFavoris.getId());

                    return ResponseEntity.status(HttpStatus.OK).body("Remove from Favorit list Successfully !!")  ;

                }
                else
                {
                    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("This user doesn't exist !!")  ;

                }


            }
            else
            {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("This product not in favoris List")  ;

            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This product nit exist !!")  ;
        }
    }

    public ResponseEntity<?> getAllFavoris(long idUser)
    {
//        Optional<User> optionalUser = userServiceCustomer.findUserById(1)  ;

            List<Favoris> optionalFavoris = favorisRepository.findByUserIdINFavoris(idUser)  ;

            if (optionalFavoris!=null)
            {
                if (!optionalFavoris.isEmpty())
                {

                    log.warn("Value od optionalFavoris >> " +optionalFavoris);
                   // log.warn("Value od optionalFavoris.getUserName >> " +optionalFavoris.get(0).getUser().getUserName());
                    //log.warn("Value od optionalFavoris >> " +optionalFavoris);

                    //Set<FavorisDto> favorisDtos = favorisMapper.mapper(optionalFavoris)  ;

                    String userName  ;
                    List<Product> products = new ArrayList<>() ;

                    log.warn("Before-----------------");
                    for (Favoris favoris:optionalFavoris)
                    {
                        products.add(favoris.getProduct()) ;
                        log.warn(favoris.getProduct().getName());
                    }
                    log.warn("After-----------------");


                    return ResponseEntity.ok(products)  ;

                }
                else
                    return ResponseEntity.status(HttpStatus.OK).body("Empty favorite List of userID : "+idUser)  ;


            }
            else
            {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Something were error when get favorite of userID : "+idUser)  ;
            }



    }

    @Override
    public ResponseEntity<?> getFavorisOfUser(long idUser) {
       List<Favoris> favorisList =  favorisRepository.findAllByUserId(idUser)  ;

       if (favorisList!=null)
       {
           if (favorisList.isEmpty())
           {
               List<FavorisDto> favorisDtos = favorisMapper.mapper(favorisList)  ;
               return ResponseEntity.ok(favorisDtos)  ;
           }
           else
               return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Doesn't have favoris !! ")  ;
       }
       else
           return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This user not found , id/User :"+idUser);
    }


}
