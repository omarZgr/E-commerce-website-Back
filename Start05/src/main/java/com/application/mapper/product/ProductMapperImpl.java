package com.application.mapper.product;

import com.application.dto.FaqDto;
import com.application.dto.FavorisDto;
import com.application.dto.ProductDto;
import com.application.dto.ReviewDto;
import com.application.entity.Faq;
import com.application.entity.Favoris;
import com.application.entity.Product;
import com.application.entity.Review;
import com.application.mapper.category.CategoryMapper;
import com.application.mapper.faq.FaqMapper;
import com.application.mapper.favoris.FavorisMapper;
import com.application.mapper.review.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapperImpl implements ProductMapper {

    private  CategoryMapper categoryMapper  ;
    private  FavorisMapper favorisMapper  ;

    @Autowired
    @Lazy
    public void setFavorisMapper(FavorisMapper favorisMapper) {
        this.favorisMapper = favorisMapper;
    }


    @Autowired
    @Lazy
    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }





    @Override
    public ProductDto mapper(Product product) {
        ProductDto productDto = new ProductDto()  ;

            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setPrice(product.getPrice());
            productDto.setDescription(product.getDescription());

            if (product.getImg()!=null)
            productDto.setByteImg(product.getImg());


            if (product.getCategory()!=null)
                productDto.setCategoryDto(categoryMapper.mapper(product.getCategory()));







        return productDto  ;


    }

    @Override
    public List<ProductDto> mapper(List<Product> products) {


        List<ProductDto> productDtos = new ArrayList<>();


        for(Product product:products)
        {
            productDtos.add(mapper(product))  ;
        }

        return productDtos  ;

    }

    @Override
    public Product unmapper(ProductDto productDto) throws IOException {



            Product product = new Product()  ;

            product.setId(productDto.getId());
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());

        if (productDto.getImg()!=null)
            product.setImg(productDto.getImg().getBytes());


        if (product.getCategory()!=null)
           product.setCategory(categoryMapper.unmapper(productDto.getCategoryDto()));






            return product  ;






    }

    @Override
    public List<Product> unmapper(List<ProductDto> productDto) throws IOException {
        List<Product> products = new ArrayList<>()  ;


        for(ProductDto product:productDto)
        {
            products.add(unmapper(product))  ;
        }

        return products  ;
    }
}
