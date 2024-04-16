package com.application.service.admin.product;

import com.application.dto.ProductDto;
import com.application.entity.Category;
import com.application.entity.Product;
import com.application.exception.HandleException;
import com.application.mapper.product.ProductMapper;
import com.application.repository.ProductRepository;
import com.application.service.admin.category.CatagoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceAdminImpl implements ProductServiceAdmin{

    private final ProductRepository productRepository ;
    private final CatagoryService catagoryService ;

    private final ProductMapper productMapper ;

    public ResponseEntity<?> getAllProduct()
    {
        List<Product> products = productRepository.findAll()  ;

        if (products.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.OK).body("Products Empty");

        }
        else
        {
            log.warn("products >>>> "+products);
            List<ProductDto> productDtos = productMapper.mapper((products))  ;
            return ResponseEntity.status(HttpStatus.OK).body(productDtos);

        }
    }


    public ResponseEntity<?> getProductById(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(productMapper.mapper(optionalProduct.get()))  ;
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new HandleException("Product not found for id: " + id)) ;
        }
    }


    public ResponseEntity<?> getProductByName(String productName) {
        log.warn("product name >> "+productName);
        List<Product> optionalProduct = productRepository.findAllByNameContaining(productName);

        if (optionalProduct!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(productMapper.mapper(optionalProduct))  ;
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new HandleException("Product not found for id: " + productName)) ;
        }
    }


    @Override
    public Optional<Product> findById(long id) {
        return productRepository.findById(id) ;
    }

    public ResponseEntity<?> addProduct(ProductDto productDto) throws IOException {

        log.warn("ProductDto >>>>>> "+productDto);


        Product product =  productMapper.unmapper(productDto)  ;

        Optional<Category> optionalCategory = catagoryService.findById(productDto.getCategoryDto().getId())  ;

        if (optionalCategory.isPresent())
        {


            product.setCategory(optionalCategory.get());



            log.warn("product >>>>>> "+product);


            Optional<Product> optionalProduct = productRepository.findByName(product.getName())  ;



            if (!optionalProduct.isPresent())
                return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.mapper(productRepository.save(product))) ;

            else

                return ResponseEntity.status(HttpStatus.CONFLICT).body(new HandleException("This name of product already exist!"))  ;

        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HandleException("(Category Not exist)Something going faild when created  product with category of  id :" +productDto.getCategoryDto().getId()))  ;

        }

    }

    public ResponseEntity<?> updateProduct(ProductDto productDto) throws IOException {

        log.warn("ProductDto >>>>>> "+productDto);


        Product product = productMapper.unmapper(productDto)  ;


        Optional<Category> optionalCategory = catagoryService.findById(productDto.getCategoryDto().getId())  ;

        if (optionalCategory.isPresent())
        {
            product.setCategory(optionalCategory.get());


            long productIdSelected = product.getId()  ;


            if (productRepository.findByName(product.getName()).isPresent())
            {
                if (productRepository.findByName(product.getName()).get().getId() == productIdSelected)
                {
                    return ResponseEntity.status(HttpStatus.OK).body(productMapper.mapper(productRepository.save(product)))  ;
                }
                else
                {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(new HandleException("This name of product already exist!"))  ;
                }

            }
            else
                return ResponseEntity.status(HttpStatus.OK).body(productMapper.mapper(productRepository.save(product)))  ;

        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HandleException("Something going faild when created  product with category of  id :" +productDto.getCategoryDto().getId()))  ;

        }



    }

    public ResponseEntity<?> deleteProduct(long productId)
    {

        Optional<Product> optionalProduct = productRepository.findById(productId) ;

        if (optionalProduct.isPresent())
        {

            try {
                productRepository.deleteById(productId);  ;
                return ResponseEntity.status(HttpStatus.OK).body("Delete Successfully of product id : "+productId ) ;

            }catch (HandleException exception)
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HandleException("Something going faild when delted product id :" +productId))  ;

            }


        }
        else
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new HandleException("This product already deleted or not exist!"))  ;




    }





}
