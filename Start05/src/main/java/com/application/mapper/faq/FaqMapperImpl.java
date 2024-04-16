package com.application.mapper.faq;


import com.application.dto.FaqDto;
import com.application.entity.Faq;
import com.application.mapper.product.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class FaqMapperImpl implements  FaqMapper{

    private  ProductMapper productMapper  ;

    @Autowired
    @Lazy
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }


    @Override
    public FaqDto mapper(Faq faq)
    {
        FaqDto faqDto= new FaqDto()  ;

        faqDto.setId(faq.getId());
        faqDto.setQuestion(faq.getQuestion());
        faqDto.setAnswer(faq.getAnswer());

        if (faq.getProduct()!=null)
              faqDto.setProductDto(productMapper.mapper(faq.getProduct()));



        return faqDto  ;
    }

    @Override
    public List<FaqDto> mapper(List<Faq> faqs)
    {

        List<FaqDto> faqDtos  = new ArrayList<>()  ;

        for (Faq faq:faqs)
            faqDtos.add(mapper(faq))  ;

        return faqDtos  ;
     }

    @Override
    public Faq unmapper(FaqDto faqDto) throws IOException {
         Faq faq= new Faq()  ;

         faq.setId(faqDto.getId());
         faq.setQuestion(faqDto.getQuestion());
         faq.setAnswer(faqDto.getAnswer());

         if (faqDto.getProductDto()!=null)
             faq.setProduct(productMapper.unmapper(faqDto.getProductDto()));

         return faq  ;
     }


    @Override
    public List<Faq> unmapper(List<FaqDto> faqDtos) throws IOException {

        List<Faq> faqs  = new ArrayList<>()  ;

        for (FaqDto faqDto:faqDtos)
            faqs.add(unmapper(faqDto))  ;

        return faqs  ;
    }



}
