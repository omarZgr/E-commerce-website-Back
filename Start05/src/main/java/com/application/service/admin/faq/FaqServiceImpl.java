package com.application.service.admin.faq;


import com.application.dto.FaqDto;
import com.application.entity.Faq;
import com.application.entity.Product;
import com.application.exception.HandleException;
import com.application.mapper.faq.FaqMapper;
import com.application.mapper.product.ProductMapper;
import com.application.repository.FaqRepository;
import com.application.service.admin.product.ProductServiceAdmin;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService{

    private final FaqRepository faqRepository  ;
    private final FaqMapper faqMapper  ;
    private final ProductMapper productMapper ;
    private final ProductServiceAdmin productService ;

    public ResponseEntity<?> getAllFaq()
    {
        List<Faq> faqs = faqRepository.findAll()  ;



        if (faqs.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.OK).body("FAQ Empty");

        }
        else
        {
            log.warn("AM HERE  >>> "+faqs.get(0).getProduct().getName());
            List<FaqDto> faqDtos = faqMapper.mapper(faqs);

            return ResponseEntity.status(HttpStatus.OK).body(faqDtos);

        }
    }

    public ResponseEntity<?> getFaqByQId(long idFaq)
    {
        Optional<Faq> optionalFaq = faqRepository.findById(idFaq);

        if (optionalFaq.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(faqMapper.mapper(optionalFaq.get()))  ;
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new HandleException("FAQ not found for idFaq : " + idFaq)) ;
        }
    }

    public ResponseEntity<?> getFaqByQuestion(String question)
    {
        Optional<Faq> optionalFaq = faqRepository.findByQuestionContaining(question);

        if (optionalFaq.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(faqMapper.mapper(optionalFaq.get()))  ;
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new HandleException("FAQ not found for question : " + question)) ;
        }
    }

    public ResponseEntity<?> getFaqByProductId(long  productId)
    {
        List<Faq> optionalFaq = faqRepository.findAllByProductId(productId);

        if (optionalFaq.size()!=0) {
            return ResponseEntity.status(HttpStatus.OK).body(faqMapper.mapper(optionalFaq))  ;
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new HandleException("FAQ not found for id Product : " + productId)) ;
        }
    }




    public ResponseEntity<?> addFaq(FaqDto faqDto)
    {

        if (!faqRepository.findByQuestionAndProductId(faqDto.getQuestion(),faqDto.getProductDto().getId()).isPresent())
        {

            try {
                Faq faq  = faqMapper.unmapper(faqDto) ;
                long productId = faqDto.getProductDto().getId()  ;
                Optional<Product> optionalProduct = productService.findById(productId) ;

                if (optionalProduct.isPresent())
                {
                    faq.setProduct(optionalProduct.get());

                    Faq faqCreated = faqRepository.save(faq)  ;


                    FaqDto faqDto1 = faqMapper.mapper(faqCreated)  ;
                   // faqDto1.setProudctId(productId);

                    return ResponseEntity.status(HttpStatus.OK).body(faqDto1)  ;
                }
                else
                {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Product Not found id:"+productId)  ;

                }

            }
            catch (Exception e)
            {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("SomeThing went  error >> "+e.getMessage())  ;

            }
        }
        else
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This question is already posted for this product")  ;

        }



 }

    @Override
    public ResponseEntity<?> updateFaq(FaqDto faqDto) {


        long idProduct = Long.parseLong(String.valueOf(faqDto.getProductDto().getId())) ;

        if (faqRepository.findByQuestionAndProductId(faqDto.getQuestion(),idProduct).isPresent())
        {
            if (faqRepository.findByQuestionAndProductIdAndId(faqDto.getQuestion(),faqDto.getProductDto().getId(), faqDto.getId()).isPresent())
            {
                {
                    try {
                        Faq faq  = faqMapper.unmapper(faqDto) ;
                        long productId = faqDto.getProductDto().getId();  ;
                        Optional<Product> optionalProduct = productService.findById(productId) ;

                        if (optionalProduct.isPresent())
                        {
                            faq.setProduct(optionalProduct.get());

                            Faq faqCreated = faqRepository.save(faq)  ;


                            FaqDto faqDto1 = faqMapper.mapper(faqCreated)  ;

                            return ResponseEntity.status(HttpStatus.OK).body(faqDto1)  ;
                        }
                        else
                        {
                            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Product Not found id:"+productId)  ;

                        }

                    }
                    catch (Exception e)
                    {
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("SomeThing went  error ")  ;

                    }
                }
            }
            else
                return ResponseEntity.status(HttpStatus.CONFLICT).body("This question is already posted for this product 1")  ;

        }
        else
        {
            try {
                Faq faq  = faqMapper.unmapper(faqDto) ;
                long productId = faqDto.getProductDto().getId();  ;
                Optional<Product> optionalProduct = productService.findById(productId) ;

                if (optionalProduct.isPresent())
                {
                    faq.setProduct(optionalProduct.get());

                    Faq faqCreated = faqRepository.save(faq)  ;


                    FaqDto faqDto1 = faqMapper.mapper(faqCreated)  ;
                   // faqDto1.setProductDto(productId);

                    return ResponseEntity.status(HttpStatus.OK).body(faqDto1)  ;
                }
                else
                {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Product Not found id:"+productId)  ;

                }

            }
            catch (Exception e)
            {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("SomeThing went  error ")  ;

            }
        }


    }


    public ResponseEntity<?> deleteFaq(long faqId)
    {

        Optional<Faq> optionalFaq = faqRepository.findById(faqId) ;

        if (optionalFaq.isPresent())
        {

            try {
                faqRepository.deleteById(faqId);  ;
                return ResponseEntity.status(HttpStatus.OK).body("Delete Successfully of faq id : "+faqId ) ;

            }catch (HandleException exception)
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HandleException("Something going faild when delted faq id :" +faqId))  ;

            }


        }
        else
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new HandleException("This product already deleted or not exist!"))  ;




    }
}
