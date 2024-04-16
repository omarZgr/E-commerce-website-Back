package com.application.mapper.faq;

import com.application.dto.FaqDto;
import com.application.entity.Faq;

import java.io.IOException;
import java.util.List;

public interface FaqMapper {

    public FaqDto mapper(Faq faq)  ;

    public List<FaqDto> mapper(List<Faq> faqs) ;

    public Faq unmapper(FaqDto faqDto) throws IOException;

    public List<Faq> unmapper(List<FaqDto> faqDtos) throws IOException;
}
