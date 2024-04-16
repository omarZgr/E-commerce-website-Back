package com.application.controller.admin;


import com.application.dto.FaqDto;
import com.application.service.admin.faq.FaqService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Log4j2
public class FaqController {

    private final FaqService faqService  ;

    @GetMapping("/faqs")
    public ResponseEntity<?> getAllFaq() {

        return faqService.getAllFaq() ;

    }


    @GetMapping("/faq/question/{question}")
    public ResponseEntity<?> getFaqByProductId(@PathVariable String question) {

        return faqService.getFaqByQuestion(question) ;

    }

    @GetMapping("/faq/search/{idFaq}")
    public ResponseEntity<?> getFaqById(@PathVariable long idFaq) {

        return faqService.getFaqByQId(idFaq) ;

    }


    @GetMapping("/faq/{productId}")
    public ResponseEntity<?> getFaqByProductId(@PathVariable long productId) {


            return faqService.getFaqByProductId(productId) ;



    }

    @PostMapping("/faq")
    public ResponseEntity<?> addFaq(@RequestBody FaqDto faqDto)
    {

        if (faqDto!=null && faqDto.getQuestion() !=null)
        {
            return faqService.addFaq(faqDto) ;


        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("FAQ was be not Null")  ;
    }


    @PutMapping("/faq/update")
    public ResponseEntity<?> updateFaq(@RequestBody FaqDto faqDto)
    {

        log.warn("faqDto >>>>>>>>>>>>>>>>> "+faqDto);
        if (faqDto!=null && faqDto.getQuestion() !=null)
        {

            return faqService.updateFaq(faqDto) ;

        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("FAQ was be not Null")  ;
    }



    @DeleteMapping("/faq/delete/{faqId}")
    public ResponseEntity<?> deleteProduct(@PathVariable long faqId)
    {
        return faqService.deleteFaq(faqId)  ;
    }



}
