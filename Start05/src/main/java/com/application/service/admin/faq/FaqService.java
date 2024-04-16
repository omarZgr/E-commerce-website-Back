package com.application.service.admin.faq;

import com.application.dto.FaqDto;
import org.springframework.http.ResponseEntity;

public interface FaqService {

    public ResponseEntity<?> getAllFaq()  ;

    public ResponseEntity<?> getFaqByQId(long idFaq)  ;

    public ResponseEntity<?> getFaqByQuestion(String question)  ;

    public ResponseEntity<?> getFaqByProductId(long  productId)  ;

    public ResponseEntity<?> addFaq(FaqDto faqDto)  ;


    public ResponseEntity<?> updateFaq(FaqDto faqDto)  ;

    public ResponseEntity<?> deleteFaq(long faqId)  ;
}
