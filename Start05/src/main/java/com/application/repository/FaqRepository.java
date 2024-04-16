package com.application.repository;

import com.application.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FaqRepository extends JpaRepository<Faq,Long> {
    Optional<Faq> findByQuestionAndProductId(String question,long id);
    Optional<Faq> findByQuestionContaining(String question);

    List<Faq> findAllByProductId(long id)  ;


    Optional<Faq> findByQuestionAndProductIdAndId(String question, long proudctId, long id);

    Optional<Faq> findByQuestion(String question);
}
